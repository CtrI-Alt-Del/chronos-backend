package br.com.chronos.server.database.jpa.solicitation.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.solicitation.models.TimePunchLogAdjustmentSolicitationModel;

@Component
public class TimePunchLogAdjustmentSolicitationMapper {

  public TimePunchLogAdjustmentSolicitationModel toModel(TimePunchLogAdjustmentSolicitation entity) {
    var senderResponsible = CollaboratorModel.builder().id(entity.getSenderResponsible().getId().value()).build();
    var replierResponsible = CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build();
    return TimePunchLogAdjustmentSolicitationModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription().value())
        .request_at(entity.getDate().value())
        .feedbackMessage(entity.getFeedbackMessage().value())
        .solicitationStatus(entity.getStatus().value())
        .time(entity.getTime().value())
        .timePunchPeriod(entity.getPeriod().name())
        .senderResponsible(senderResponsible)
        .replierResponsible(replierResponsible).build();

  }

  public TimePunchLogAdjustmentSolicitation toEntity(TimePunchLogAdjustmentSolicitationModel model) {

    var senderResponsibleDto = new ResponsibleDto()
        .setId(model.getSenderResponsible().getId().toString())
        .setName(model.getSenderResponsible().getName())
        .setEmail(model.getSenderResponsible().getAccount().getEmail())
        .setRole(model.getSenderResponsible().getAccount().getRole().toString());

    var senderResponsibleAggregateDto = new ResponsibleAggregateDto()
        .setId(model.getSenderResponsible().getId().toString())
        .setResponsibleDto(senderResponsibleDto);

    var replierResponsibleDto = new ResponsibleDto()
        .setId(model.getReplierResponsible().getId().toString())
        .setName(model.getReplierResponsible().getName())
        .setEmail(model.getReplierResponsible().getAccount().getEmail())
        .setRole(model.getReplierResponsible().getAccount().getRole().toString());

    var replierResponsibleAggregateDto = new ResponsibleAggregateDto()
        .setId(model.getReplierResponsible().getId().toString())
        .setResponsibleDto(replierResponsibleDto);

    var dto = new TimePunchLogAdjustmentSolicitationDto()
        .setTime(model.getTime())
        .setId(model.getId().toString())
        .setDescription(model.getDescription().toString())
        .setDate(model.getRequest_at())
        .setStatus(model.getSolicitationStatus().toString())
        .setFeedbackMessage(model.getFeedbackMessage().toString())
        .setSenderResponsible(senderResponsibleAggregateDto)
        .setReplierResponsible(replierResponsibleAggregateDto);

    return new TimePunchLogAdjustmentSolicitation(dto);
  }
}
