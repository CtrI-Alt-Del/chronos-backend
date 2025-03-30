package br.com.chronos.core.modules.solicitation.use_cases;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;

public class CreateTimePunchLogAdjustmentSolicitationUseCase {

  private final SolicitationsRepository solicitationsRepository;
  private final CollaboratorsRepository collaboratorsRepository;

  public CreateTimePunchLogAdjustmentSolicitationUseCase(SolicitationsRepository solicitationsRepository,CollaboratorsRepository collaboratorsRepository) {
    this.collaboratorsRepository = collaboratorsRepository;
    this.solicitationsRepository = solicitationsRepository;
  }

  public TimePunchLogAdjustmentSolicitationDto execute(TimePunchLogAdjustmentSolicitationDto dto, Account sender) {
    var senderResponsibleDto = new ResponsibleDto()
        .setId(sender.getCollaboratorId().value().toString())
        .setEmail(sender.getEmail().value().toString())
        .setRole(sender.getRole().value().toString())
        .setName(findSenderName(sender.getCollaboratorId()));
    dto.setSenderResponsible(new ResponsibleAggregateDto(senderResponsibleDto));
    var solicitation = new TimePunchLogAdjustmentSolicitation(dto);
    solicitationsRepository.addTimePunchLogAdjustmentSolicitation(solicitation);
    return solicitation.getDto();
  }

  private String findSenderName(Id collaboratorId) {
    var collaborator = collaboratorsRepository.findById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new NotFoundException("Collaborator not found");
    }
    return collaborator.get().getName().value().toString();
  }

}
