package br.com.chronos.server.database.jpa.portal.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.portal.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.portal.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.portal.models.DayOffScheduleAdjustmentSolicitationModel;

@Component
public class DayOffScheduleAdjustmentSolicitationMapper {


  public DayOffScheduleAdjustmentSolicitationModel toModel(DayOffScheduleAdjustmentSolicitation entity) {
    var senderResponsible = CollaboratorModel.builder().id(entity.getSenderResponsible().getId().value()).build();
    var replierResponsible = (entity.getReplierResponsible() != null)
        ? CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build()
        : null;
    var daysOff = entity.getDayOffSchedule().getDaysOff().map(date -> date.value()).list();
    var solicitationModel = DayOffScheduleAdjustmentSolicitationModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription() != null ? entity.getDescription().value() : null)
        .date(entity.getDate().value())
        .feedbackMessage(entity.getFeedbackMessage() != null ? entity.getFeedbackMessage().value() : null)
        .solicitationStatus(entity.getStatus().value())
        .senderResponsible(senderResponsible)
        .replierResponsible(replierResponsible)
        .daysOffCount(entity.getDayOffSchedule().getDaysOffCount().integer().value())
        .workDaysCount(entity.getDayOffSchedule().getWorkdaysCount().integer().value())
        .daysOff(daysOff)
        .build();

    return solicitationModel;
  }

  public DayOffScheduleAdjustmentSolicitationDto toDto(DayOffScheduleAdjustmentSolicitationModel model) {
    var senderResponsibleDto = new ResponsibleDto()
        .setId(model.getSenderResponsible().getId().toString())
        .setName(model.getSenderResponsible().getName())
        .setEmail(model.getSenderResponsible().getAccount().getEmail())
        .setRole(model.getSenderResponsible().getAccount().getRole().toString())
        .setCpf(model.getSenderResponsible().getCpf())
        .setSector(model.getSenderResponsible().getAccount().getSector().toString());
    var senderResponsibleAggregateDto = new ResponsibleAggregateDto(senderResponsibleDto);

    ResponsibleAggregateDto replierResponsibleAggregateDto = null;
    if (model.getReplierResponsible() != null) {
      var replierResponsibleDto = new ResponsibleDto()
          .setId(model.getReplierResponsible().getId().toString())
          .setName(model.getReplierResponsible().getName())
          .setCpf(model.getReplierResponsible().getCpf())
          .setSector(model.getReplierResponsible().getAccount().getSector().toString())
          .setEmail(model.getReplierResponsible().getAccount().getEmail())
          .setRole(model.getReplierResponsible().getAccount().getRole().toString());

      replierResponsibleAggregateDto = new ResponsibleAggregateDto(replierResponsibleDto);
    }

    String description = model.getDescription() != null ? model.getDescription().toString() : null;
    String feedbackMessage = model.getFeedbackMessage() != null ? model.getFeedbackMessage().toString() : null;


    var daysOffScheduleDto = new DayOffScheduleDto()
        .setId(model.getId().toString())
        .setWorkdaysCount(model.getWorkDaysCount())
        .setDaysOffCount(model.getDaysOffCount())
        .setDaysOff(model.getDaysOff());

    var dto = new DayOffScheduleAdjustmentSolicitationDto()
        .setId(model.getId().toString())
        .setDescription(description)
        .setDate(model.getDate())
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
