package br.com.chronos.server.database.jpa.portal.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.portal.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.portal.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.portal.models.TimePunchLogAdjustmentSolicitationModel;

@Component
public class TimePunchLogAdjustmentSolicitationMapper {

  public TimePunchLogAdjustmentSolicitationModel toModel(TimePunchLogAdjustmentSolicitation entity) {
    var senderResponsible = CollaboratorModel.builder().id(entity.getSenderResponsible().getId().value()).build();
    var replierResponsible = (entity.getReplierResponsible() != null)
        ? CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build()
        : null;
    return TimePunchLogAdjustmentSolicitationModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription() != null ? entity.getDescription().value() : null)
        .date(entity.getDate().value())
        .feedbackMessage(entity.getFeedbackMessage() != null ? entity.getFeedbackMessage().value() : null)
        .solicitationStatus(entity.getStatus().value())
        .time(entity.getTime().value())
        .timePunchPeriod(entity.getPeriod().name())
        .senderResponsible(senderResponsible)
        .replierResponsible(replierResponsible)
        .date(entity.getWorkdayLogDate().value())
        .reason(entity.getReason().value())
        .build();

  }

  public TimePunchLogAdjustmentSolicitationDto toDto(TimePunchLogAdjustmentSolicitationModel model) {

    var senderResponsibleDto = new ResponsibleDto()
        .setId(model.getSenderResponsible().getId().toString())
        .setName(model.getSenderResponsible().getName())
        .setEmail(model.getSenderResponsible().getAccount().getEmail())
        .setCpf(model.getSenderResponsible().getCpf())
        .setSector(model.getSenderResponsible().getAccount().getSector().toString())
        .setRole(model.getSenderResponsible().getAccount().getRole().toString());

    var senderResponsibleAggregateDto = new ResponsibleAggregateDto(senderResponsibleDto);

    ResponsibleAggregateDto replierResponsibleAggregateDto = null;
    if (model.getReplierResponsible() != null) {
      var replierResponsibleDto = new ResponsibleDto()
          .setId(model.getReplierResponsible().getId().toString())
          .setName(model.getReplierResponsible().getName())
          .setEmail(model.getReplierResponsible().getAccount().getEmail())
          .setCpf(model.getReplierResponsible().getCpf())
          .setSector(model.getSenderResponsible().getAccount().getSector().toString())
          .setRole(model.getReplierResponsible().getAccount().getRole().toString());

      replierResponsibleAggregateDto = new ResponsibleAggregateDto(replierResponsibleDto);
    }

    String description = model.getDescription() != null ? model.getDescription().toString() : null;
    String feedbackMessage = model.getFeedbackMessage() != null ? model.getFeedbackMessage().toString() : null;

    var dto = new TimePunchLogAdjustmentSolicitationDto()
        .setTime(model.getTime())
        .setPeriod(model.getTimePunchPeriod().toString())
        .setId(model.getId().toString())
        .setDescription(description)
        .setDate(model.getDate())
        .setStatus(model.getSolicitationStatus().toString())
        .setFeedbackMessage(feedbackMessage)
        .setSenderResponsible(senderResponsibleAggregateDto)
        .setReplierResponsible(replierResponsibleAggregateDto)
        .setWorkdayLogDate(model.getDate())
        .setReason(model.getReason().toString());

    return dto;
  }

  public TimePunchLogAdjustmentSolicitation toEntity(TimePunchLogAdjustmentSolicitationModel model) {
    return new TimePunchLogAdjustmentSolicitation(toDto(model));
  }
}
