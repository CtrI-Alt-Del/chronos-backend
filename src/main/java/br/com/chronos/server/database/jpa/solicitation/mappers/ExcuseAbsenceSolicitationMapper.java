package br.com.chronos.server.database.jpa.solicitation.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.solicitation.domain.dtos.ExcuseAbsenceSolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.ExcuseAbsenceSolicitation;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.solicitation.models.ExcuseAbsenceSolicitationModel;

@Component
public class ExcuseAbsenceSolicitationMapper {

  @Autowired
  private JustificationMapper justificationMapper;

  public ExcuseAbsenceSolicitationModel toModel(ExcuseAbsenceSolicitation entity) {
    var senderResponse = CollaboratorModel.builder().id(entity.getSenderResponsible().getId().value()).build();
    var replierResponse = (entity.getReplierResponsible() != null)
        ? CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build()
        : null;
    var justification = entity.getJustification() != null
        ? justificationMapper.toModel(entity.getJustification())
        : null;
    var solicitationModel = ExcuseAbsenceSolicitationModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription() != null ? entity.getDescription().value() : null)
        .date(entity.getDate().value())
        .feedbackMessage(entity.getFeedbackMessage() != null ? entity.getFeedbackMessage().value() : null)
        .solicitationStatus(entity.getStatus().value())
        .senderResponsible(senderResponse)
        .replierResponsible(replierResponse)
        .excuseAbsenceDate(entity.getExcuseAbsenceDate().value())
        .justification(justification)
        .build();
    return solicitationModel;
  }

  public ExcuseAbsenceSolicitationDto toDto(ExcuseAbsenceSolicitationModel model) {
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
    var justificationDto = model.getJustification() != null
        ? justificationMapper.toDto(model.getJustification())
        : null;
    return new ExcuseAbsenceSolicitationDto()
        .setId(model.getId().toString())
        .setDescription(model.getDescription())
        .setDate(model.getDate())
        .setFeedbackMessage(model.getFeedbackMessage())
        .setStatus(model.getSolicitationStatus().toString())
        .setSenderResponsible(senderResponsibleAggregateDto)
        .setReplierResponsible(replierResponsibleAggregateDto)
        .setExcuseAbsenceDate(model.getExcuseAbsenceDate())
        .setJustification(justificationDto);
  }

  public ExcuseAbsenceSolicitation toEntity(ExcuseAbsenceSolicitationModel model) {
    return new ExcuseAbsenceSolicitation(toDto(model));
  }

}
