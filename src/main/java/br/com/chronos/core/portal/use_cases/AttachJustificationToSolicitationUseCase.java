package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.AttachmentDto;
import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.JustificationDto;
import br.com.chronos.core.portal.domain.dtos.SolicitationDto;
import br.com.chronos.core.portal.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.portal.domain.entities.Justification;
import br.com.chronos.core.portal.domain.entities.WorkLeaveSolicitation;
import br.com.chronos.core.portal.interfaces.repositories.JustificationRepository;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class AttachJustificationToSolicitationUseCase {
  private final SolicitationsRepository solicitationsRepository;
  private final JustificationRepository justificationRepository;

  public AttachJustificationToSolicitationUseCase(
      SolicitationsRepository solicitationsRepository,
      JustificationRepository justificationRepository) {
    this.justificationRepository = justificationRepository;
    this.solicitationsRepository = solicitationsRepository;
  }

  public SolicitationDto execute(String solicitationId,
      JustificationDto justificationDto, AttachmentDto attachmentDto) {

    if (attachmentDto != null) {
      justificationDto.setAttachment(attachmentDto);
    }

    var justification = new Justification(justificationDto);
    var solicitation = findById(Id.create(solicitationId));
    justificationRepository.add(justification);

    if (solicitation.getType().isExcusedAbsence().isTrue()) {
      var excuseAbsenceSolicitation = (ExcusedAbsenceSolicitation) solicitation;
      solicitationsRepository.addJustificationToSolicitation(excuseAbsenceSolicitation, justification);
    } else if (solicitation.getType().isWorkLeave().isTrue()) {
      var workLeaveSolicitation = (WorkLeaveSolicitation) solicitation;
      solicitationsRepository.addJustificationToSolicitation(workLeaveSolicitation, justification);
    }

    return findById(Id.create(solicitationId)).getDto();
  }

  private Solicitation findById(Id solicitationId) {
    var solicitation = solicitationsRepository.findById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new NotFoundException("Solicitacao nao encontrada");
    }
    return solicitation.get();
  }
}
