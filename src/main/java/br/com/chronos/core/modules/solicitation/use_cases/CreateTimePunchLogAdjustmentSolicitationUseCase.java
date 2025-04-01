package br.com.chronos.core.modules.solicitation.use_cases;

import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Role;
import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationStatus.Status;
import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public class CreateTimePunchLogAdjustmentSolicitationUseCase {

  private final SolicitationsRepository solicitationsRepository;
  private final CollaboratorsRepository collaboratorsRepository;

  public CreateTimePunchLogAdjustmentSolicitationUseCase(SolicitationsRepository solicitationsRepository,
      CollaboratorsRepository collaboratorsRepository) {
    this.collaboratorsRepository = collaboratorsRepository;
    this.solicitationsRepository = solicitationsRepository;
  }

  public TimePunchLogAdjustmentSolicitationDto execute(TimePunchLogAdjustmentSolicitationDto dto, Id collaboratorId,
      Email senderEmail, Role senderRole) {
    var senderResponsibleDto = new ResponsibleDto()
        .setId(collaboratorId.value().toString())
        .setEmail(senderEmail.value().toString())
        .setRole(senderRole.value().toString())
        .setName(findSenderName(collaboratorId));
    dto.setSenderResponsible(new ResponsibleAggregateDto(senderResponsibleDto));
    var solicitation = new TimePunchLogAdjustmentSolicitation(dto);
    if (solicitation.status.value() != Status.PENDING) {
      throw new ValidationException("status", "deve ser pendente");
    }
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
