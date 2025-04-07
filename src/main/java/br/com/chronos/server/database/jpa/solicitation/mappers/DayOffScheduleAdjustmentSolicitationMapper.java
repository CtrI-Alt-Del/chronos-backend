package br.com.chronos.server.database.jpa.solicitation.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.solicitation.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.modules.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.solicitation.models.DayOffScheduleAdjustmentSolicitationModel;
import br.com.chronos.server.database.jpa.work_schedule.mappers.DayOffMapper;

@Component
public class DayOffScheduleAdjustmentSolicitationMapper {

  @Autowired
  private DayOffMapper dayOffMapper;

  public DayOffScheduleAdjustmentSolicitationModel toModel(DayOffScheduleAdjustmentSolicitation entity) {
    var senderResponsible = CollaboratorModel.builder().id(entity.getSenderResponsible().getId().value()).build();
    var replierResponsible = (entity.getReplierResponsible() != null)
        ? CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build()
        : null;
    var solicitationModel = DayOffScheduleAdjustmentSolicitationModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription() != null ? entity.getDescription().value() : null)
        .requestedAt(entity.getDate().value())
        .feedbackMessage(entity.getFeedbackMessage() != null ? entity.getFeedbackMessage().value() : null)
        .solicitationStatus(entity.getStatus().value())
        .senderResponsible(senderResponsible)
        .replierResponsible(replierResponsible)
        .daysOffCount(entity.getDayOffSchedule().getDaysOffCount().integer().value())
        .workDaysCount(entity.getDayOffSchedule().getWorkdaysCount().integer().value())
        .build();

    return solicitationModel;
  }

  public DayOffScheduleAdjustmentSolicitationDto toDto(DayOffScheduleAdjustmentSolicitationModel model) {
    var senderResponsibleDto = new ResponsibleDto()
        .setId(model.getSenderResponsible().getId().toString())
        .setName(model.getSenderResponsible().getName())
        .setEmail(model.getSenderResponsible().getAccount().getEmail())
        .setRole(model.getSenderResponsible().getAccount().getRole().toString());

    var senderResponsibleAggregateDto = new ResponsibleAggregateDto(senderResponsibleDto);

    ResponsibleAggregateDto replierResponsibleAggregateDto = null;
    if (model.getReplierResponsible() != null) {
      var replierResponsibleDto = new ResponsibleDto()
          .setId(model.getReplierResponsible().getId().toString())
          .setName(model.getReplierResponsible().getName())
          .setEmail(model.getReplierResponsible().getAccount().getEmail())
          .setRole(model.getReplierResponsible().getAccount().getRole().toString());

      replierResponsibleAggregateDto = new ResponsibleAggregateDto(replierResponsibleDto);
    }

    String description = model.getDescription() != null ? model.getDescription().toString() : null;
    String feedbackMessage = model.getFeedbackMessage() != null ? model.getFeedbackMessage().toString() : null;

    var daysOffDto = Array.createFrom(model.getDaysOff(), dayOffMapper::toDto).list();

    var daysOffScheduleDto = new DayOffScheduleDto()
        .setId(model.getId().toString())
        .setWorkdaysCount(model.getWorkDaysCount())
        .setDaysOffCount(model.getDaysOffCount())
        .setDaysOff(daysOffDto);

    var dto = new DayOffScheduleAdjustmentSolicitationDto()
        .setId(model.getId().toString())
        .setDescription(description)
        .setDate(model.getRequestedAt())
        .setStatus(model.getSolicitationStatus().toString())
        .setFeedbackMessage(feedbackMessage)
        .setSenderResponsible(senderResponsibleAggregateDto)
        .setReplierResponsible(replierResponsibleAggregateDto)
        .setDayOffSchedule(daysOffScheduleDto);

    return dto;

  }

  public DayOffScheduleAdjustmentSolicitation toEntity(DayOffScheduleAdjustmentSolicitationModel model) {
    return new DayOffScheduleAdjustmentSolicitation(toDto(model));
  }
}
