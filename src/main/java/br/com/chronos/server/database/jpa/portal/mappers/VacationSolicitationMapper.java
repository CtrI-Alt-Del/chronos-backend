package br.com.chronos.server.database.jpa.portal.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.portal.domain.dtos.VacationSolicitationDto;
import br.com.chronos.core.portal.domain.entities.VacationSolicitation;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.portal.models.VacationSolicitationModel;

@Component
public class VacationSolicitationMapper {
  public VacationSolicitationModel toModel(VacationSolicitation entity) {
    var senderResponse = CollaboratorModel
        .builder()
        .id(entity.getSenderResponsible().getId().value()).build();
    var replierResponse = (entity.getReplierResponsible() != null)
        ? CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build()
        : null;
    var solicitationModel = VacationSolicitationModel.builder()
        .id(entity.getId().value())
        .description(
            entity.getDescription() != null
                ? entity.getDescription().value()
                : null)
        .date(entity.getDate().value())
        .feedbackMessage(
            entity.getFeedbackMessage() != null
                ? entity.getFeedbackMessage().value()
                : null)
        .solicitationStatus(entity.getStatus().value())
        .senderResponsible(senderResponse)
        .replierResponsible(replierResponse)
        .vacationDays(entity.getVacationDays().map(date -> date.value()).list())
        .build();
    return solicitationModel;
  }

  public VacationSolicitationDto toDto(VacationSolicitationModel model) {
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
          .setCpf(model.getSenderResponsible().getCpf())
          .setSector(model.getSenderResponsible().getAccount().getSector().toString())
          .setRole(model.getReplierResponsible().getAccount().getRole().toString());
      replierResponsibleAggregateDto = new ResponsibleAggregateDto(replierResponsibleDto);
    }

    return new VacationSolicitationDto()
        .setId(model.getId().toString())
        .setDescription(model.getDescription())
        .setDate(model.getDate())
        .setFeedbackMessage(model.getFeedbackMessage())
        .setStatus(model.getSolicitationStatus().toString())
        .setSenderResponsible(senderResponsibleAggregateDto)
        .setReplierResponsible(replierResponsibleAggregateDto)
        .setVacationDays(model.getVacationDays());
  }

  public VacationSolicitation toEntity(VacationSolicitationModel model) {
    return new VacationSolicitation(toDto(model));
  }
}
