package br.com.chronos.server.database.jpa.solicitation.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.solicitation.domain.dtos.WorkScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.entities.WorkScheduleAdjustmentSolicitation;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.solicitation.models.WorkScheduleAdjustmentSolicitationModel;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkScheduleMapper;

@Component
public class WorkScheduleAdjustmentSolicitationMapper {
  @Autowired
  private WorkScheduleMapper workScheduleMapper;

  public WorkScheduleAdjustmentSolicitationModel toModel(WorkScheduleAdjustmentSolicitation entity) {
    var senderResponsible = CollaboratorModel.builder().id(entity.getSenderResponsible().getId().value()).build();
    var replierResponsible = CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build();
    var workScheduleModel = workScheduleMapper.toModel(entity.getWorkSchedule());
    var solicitationModel = WorkScheduleAdjustmentSolicitationModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription().value())
        .requestedAt(entity.getDate().value())
        .feedbackMessage(entity.getFeedbackMessage().value())
        .solicitationStatus(entity.getStatus().value())
        .senderResponsible(senderResponsible)
        .replierResponsible(replierResponsible)
        .workSchedule(workScheduleModel).build();

    return solicitationModel;
  }

  public WorkScheduleAdjustmentSolicitation toEntity(WorkScheduleAdjustmentSolicitationModel model) {
    var senderResponsibleDto = new ResponsibleDto()
        .setId(model.getSenderResponsible().getId().toString())
        .setName(model.getSenderResponsible().getName())
        .setEmail(model.getSenderResponsible().getAccount().getEmail())
        .setRole(model.getSenderResponsible().getAccount().getRole().toString());

    var senderResponsibleAggregateDto = new ResponsibleAggregateDto(senderResponsibleDto);

    var replierResponsibleDto = new ResponsibleDto()
        .setId(model.getReplierResponsible().getId().toString())
        .setName(model.getReplierResponsible().getName())
        .setEmail(model.getReplierResponsible().getAccount().getEmail())
        .setRole(model.getReplierResponsible().getAccount().getRole().toString());

    var replierResponsibleAggregateDto = new ResponsibleAggregateDto(replierResponsibleDto);

    var workSchedule = workScheduleMapper.toEntity(model.getWorkSchedule());

    var dto = new WorkScheduleAdjustmentSolicitationDto()
        .setId(model.getId().toString())
        .setDescription(model.getDescription().toString())
        .setDate(model.getRequestedAt())
        .setStatus(model.getSolicitationStatus().toString())
        .setFeedbackMessage(model.getFeedbackMessage().toString())
        .setSenderResponsible(senderResponsibleAggregateDto)
        .setReplierResponsible(replierResponsibleAggregateDto)
        .setWorkScheduleDto(workSchedule.getDto());
    return new WorkScheduleAdjustmentSolicitation(dto);

  }
}
