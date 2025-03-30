package br.com.chronos.core.modules.solicitation.use_cases;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.solicitation.domain.dtos.WorkScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.entities.WorkScheduleAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class CreateWorkScheduleAdjustmentSolicitationUseCase {

  private final SolicitationsRepository solicitationsRepository;
  private final WorkSchedulesRepository workSchedulesRepository;
  private final CollaboratorsRepository collaboratorsRepository;

  public CreateWorkScheduleAdjustmentSolicitationUseCase(
      SolicitationsRepository solicitationsRepository,
      WorkSchedulesRepository workSchedulesRepository,
      CollaboratorsRepository collaboratorsRepository) {
    this.workSchedulesRepository = workSchedulesRepository;
    this.solicitationsRepository = solicitationsRepository;
    this.collaboratorsRepository = collaboratorsRepository;
  }

  public WorkScheduleAdjustmentSolicitationDto execute(WorkScheduleAdjustmentSolicitationDto dto, Account sender) {
    var workSchedule = findWorkScheduleById(Id.create(dto.workScheduleId));
    var senderResponsibleDto = new ResponsibleDto()
        .setId(sender.getCollaboratorId().value().toString())
        .setEmail(sender.getEmail().value().toString())
        .setRole(sender.getRole().value().toString())
        .setName(findSenderName(sender.getCollaboratorId()));
    dto.setSenderResponsible(new ResponsibleAggregateDto(senderResponsibleDto));
    var solicitation = new WorkScheduleAdjustmentSolicitation(dto);
    solicitationsRepository.addWorkScheduleAdjustmentSolicitation(solicitation);
    return solicitation.getDto();
  }

  private WorkSchedule findWorkScheduleById(Id id) {
    var workSchedule = workSchedulesRepository.findById(id);
    if (workSchedule.isEmpty()) {
      throw new NotFoundException("Work schedule not found");
    }
    return workSchedule.get();
  }

  private String findSenderName(Id collaboratorId) {
    var collaborator = collaboratorsRepository.findById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new NotFoundException("Collaborator not found");
    }
    return collaborator.get().getName().value().toString();
  }
}
