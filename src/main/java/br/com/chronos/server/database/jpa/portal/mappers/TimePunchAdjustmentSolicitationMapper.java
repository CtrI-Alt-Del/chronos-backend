package br.com.chronos.server.database.jpa.portal.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.portal.domain.dtos.TimePunchAdjustmentSolicitationDto;
import br.com.chronos.core.portal.domain.entities.TimePunchAdjustmentSolicitation;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.portal.models.TimePunchAdjustmentSolicitationModel;

@Component
public class TimePunchAdjustmentSolicitationMapper {
  public TimePunchAdjustmentSolicitationModel toModel(TimePunchAdjustmentSolicitation entity) {
    var senderResponse = CollaboratorModel
        .builder()
        .id(entity.getSenderResponsible().getId().value())
        .build();
    var replierResponse = (entity.getReplierResponsible() != null)
        ? CollaboratorModel
            .builder()
            .id(entity.getReplierResponsible().getId().value())
            .build()
        : null;

    return TimePunchAdjustmentSolicitationModel
        .builder()
        .id(entity.getId().value())
        .date(entity.getDate().value())
        .feedbackMessage(
            entity.getFeedbackMessage() != null ? entity.getFeedbackMessage().value() : null)
        .solicitationStatus(entity.getStatus().value())
        .senderResponsible(senderResponse)
        .replierResponsible(replierResponse)
        .build();

  }

  public TimePunchAdjustmentSolicitationDto toDto(TimePunchAdjustmentSolicitationModel model) {
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
          .setSector(model.getReplierResponsible().getAccount().getSector().toString())
          .setRole(model.getReplierResponsible().getAccount().getRole().toString());
      replierResponsibleAggregateDto = new ResponsibleAggregateDto(replierResponsibleDto);
    }

    return (TimePunchAdjustmentSolicitationDto) new TimePunchAdjustmentSolicitationDto()
        .setId(model.getId().toString())
        .setType("PAID_OVERTIME")
        .setDate(model.getDate())
        .setFeedbackMessage(model.getFeedbackMessage())
        .setStatus(model.getSolicitationStatus().toString())
        .setSenderResponsible(senderResponsibleAggregateDto)
        .setReplierResponsible(replierResponsibleAggregateDto);
  }

  public TimePunchAdjustmentSolicitation toEntity(TimePunchAdjustmentSolicitationModel model) {
    return new TimePunchAdjustmentSolicitation(toDto(model));
  }
}
