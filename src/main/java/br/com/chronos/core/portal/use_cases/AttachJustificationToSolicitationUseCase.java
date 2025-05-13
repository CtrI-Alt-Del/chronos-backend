package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.AttachmentDto;
import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.dtos.ExcusedAbsenceSolicitationDto;
import br.com.chronos.core.portal.domain.dtos.JustificationDto;
import br.com.chronos.core.portal.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.portal.domain.entities.Justification;
import br.com.chronos.core.portal.interfaces.repositories.JustificationRepository;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class AttachJustificationToSolicitationUseCase {
  private final SolicitationsRepository solicitationsRepository;
  private final JustificationRepository justificationRepository;

  public AttachJustificationToSolicitationUseCase(
      SolicitationsRepository solicitationsRepository,
      JustificationRepository justificationRepository){
    this.justificationRepository = justificationRepository;
    this.solicitationsRepository = solicitationsRepository;
  }

  public ExcusedAbsenceSolicitationDto execute(String solicitationId,
      JustificationDto justificationDto,AttachmentDto attachmentDto) {

    if(attachmentDto != null ){
      justificationDto.setAttachment(attachmentDto);
    }

    var justification = new Justification(justificationDto);
    var solicitation = findSolicitation(Id.create(solicitationId));

    justificationRepository.add(justification);
    solicitationsRepository.addJustificationToSolicitation(solicitation,
        justification);

    return findSolicitation(Id.create(solicitationId)).getDto();
  }

  private ExcusedAbsenceSolicitation findSolicitation(Id solicitationId) {
    var solicitation = solicitationsRepository.findById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new NotFoundException("Solicitacao nao encontrada");
    }
    return (ExcusedAbsenceSolicitation) solicitation.get();
  }
}
