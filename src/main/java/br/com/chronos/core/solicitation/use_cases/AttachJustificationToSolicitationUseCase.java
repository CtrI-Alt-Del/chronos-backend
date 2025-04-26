package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.dtos.ExcuseAbsenceSolicitationDto;
import br.com.chronos.core.solicitation.domain.dtos.JustificationDto;
import br.com.chronos.core.solicitation.domain.entities.ExcuseAbsenceSolicitation;
import br.com.chronos.core.solicitation.domain.entities.Justification;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class AttachJustificationToSolicitationUseCase {
  private final SolicitationsRepository solicitationsRepository;
  private final JustificationRepository justificationRepository;

  public AttachJustificationToSolicitationUseCase(SolicitationsRepository solicitationsRepository,JustificationRepository justificationRepository) {
    this.justificationRepository = justificationRepository;
    this.solicitationsRepository = solicitationsRepository;
  }

  public ExcuseAbsenceSolicitationDto execute(String solicitationId, JustificationDto justificationDto) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    var justification = new Justification(justificationDto);
    justificationRepository.add(justification);
    solicitationsRepository.addJustificationToSolicitation(solicitation, justification);
    return findSolicitation(Id.create(solicitationId)).getDto();
  }

  private ExcuseAbsenceSolicitation findSolicitation(Id solicitationId) {
    var solicitation = solicitationsRepository.findSolicitationById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new NotFoundException("Solicitacao nao encontrada");
    }
    return (ExcuseAbsenceSolicitation) solicitation.get();
  }

}
