package br.com.chronos.server.database.jpa.solicitation.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.solicitation.domain.entities.DayOffSolicitation;
import br.com.chronos.core.solicitation.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.solicitation.models.DayOffScheduleAdjustmentSolicitationModel;
import br.com.chronos.server.database.jpa.solicitation.models.DayOffSolicitationModel;
import br.com.chronos.server.database.jpa.solicitation.models.ExcusedAbsenceSolicitationModel;
import br.com.chronos.server.database.jpa.solicitation.models.SolicitationModel;
import br.com.chronos.server.database.jpa.solicitation.models.TimePunchLogAdjustmentSolicitationModel;

@Component
public class SolicitationMapper {

  @Autowired
  private DayOffScheduleAdjustmentSolicitationMapper dayOffScheduleAdjustmentSolicitationMapper;

  @Autowired
  private TimePunchLogAdjustmentSolicitationMapper timePunchLogAdjustmentSolicitationMapper;

  @Autowired
  private DayOffSolicitationMapper dayOffSolicitationMapper;

  @Autowired
  private ExcusedAbsenceSolicitationMapper excusedAbsenceSolicitationMapper;

  public Solicitation toEntity(SolicitationModel model) {
    if (model instanceof DayOffScheduleAdjustmentSolicitationModel) {
      return dayOffScheduleAdjustmentSolicitationMapper.toEntity((DayOffScheduleAdjustmentSolicitationModel) model);
    } else if (model instanceof TimePunchLogAdjustmentSolicitationModel) {
      return timePunchLogAdjustmentSolicitationMapper.toEntity((TimePunchLogAdjustmentSolicitationModel) model);
    } else if (model instanceof DayOffSolicitationModel) {
      return dayOffSolicitationMapper.toEntity((DayOffSolicitationModel) model);
    } else if (model instanceof ExcusedAbsenceSolicitationModel) {
      return excusedAbsenceSolicitationMapper.toEntity((ExcusedAbsenceSolicitationModel) model);
    }
    throw new IllegalArgumentException("Unknown solicitation type: " + model.getClass());
  }

  public SolicitationModel toModel(Solicitation entity) {
    if (entity.getType().isTimePunch().isTrue()) {
      return dayOffScheduleAdjustmentSolicitationMapper.toModel((DayOffScheduleAdjustmentSolicitation) entity);
    } else if (entity.getType().isDayOffSchedule().isTrue()) {
      return timePunchLogAdjustmentSolicitationMapper.toModel((TimePunchLogAdjustmentSolicitation) entity);
    } else if (entity.getType().isDayOff().isTrue()) {
      return dayOffSolicitationMapper.toModel((DayOffSolicitation) entity);
    } else if (entity.getType().isExcusedAbsence().isTrue()) {
      return excusedAbsenceSolicitationMapper.toModel((ExcusedAbsenceSolicitation) entity);
    }
    throw new IllegalArgumentException("Unknown solicitation type: " + entity.getClass());
  }

  protected void mapCommonFields(SolicitationModel model, Solicitation entity) {
    var senderResponsible = CollaboratorModel.builder()
        .id(entity.getSenderResponsible().getId().value()).build();
    var replierResponsible = (entity.getReplierResponsible() != null)
        ? CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build()
        : null;

    model.setId(entity.getId().value());
    model.setDescription(entity.getDescription() != null ? entity.getDescription().value() : null);
    model.setDate(entity.getDate().value());
    model.setSolicitationStatus(entity.getStatus().value());
    model.setSenderResponsible(senderResponsible);
    model.setReplierResponsible(replierResponsible);
    model.setFeedbackMessage(entity.getFeedbackMessage() != null ? entity.getFeedbackMessage().value() : null);
  }

  // To convert the model to DTO (Data Transfer Object)
  public SolicitationDto toDto(SolicitationModel model) {
    ResponsibleDto senderResponsibleDto = new ResponsibleDto()
        .setId(model.getSenderResponsible().getId().toString())
        .setName(model.getSenderResponsible().getName())
        .setEmail(model.getSenderResponsible().getAccount().getEmail())
        .setCpf(model.getSenderResponsible().getCpf())
        .setSector(model.getSenderResponsible().getAccount().getSector().toString())
        .setRole(model.getSenderResponsible().getAccount().getRole().toString());

    ResponsibleAggregateDto senderResponsibleAggregateDto = new ResponsibleAggregateDto(senderResponsibleDto);

    ResponsibleAggregateDto replierResponsibleAggregateDto = null;
    if (model.getReplierResponsible() != null) {
      ResponsibleDto replierResponsibleDto = new ResponsibleDto()
          .setId(model.getReplierResponsible().getId().toString())
          .setName(model.getReplierResponsible().getName())
          .setEmail(model.getReplierResponsible().getAccount().getEmail())
          .setCpf(model.getReplierResponsible().getCpf())
          .setRole(model.getReplierResponsible().getAccount().getRole().toString());

      replierResponsibleAggregateDto = new ResponsibleAggregateDto(replierResponsibleDto);
    }

    return new SolicitationDto()
        .setId(model.getId().toString())
        .setDescription(model.getDescription())
        .setDate(model.getDate())
        .setStatus(model.getSolicitationStatus().toString())
        .setFeedbackMessage(model.getFeedbackMessage())
        .setSenderResponsible(senderResponsibleAggregateDto)
        .setReplierResponsible(replierResponsibleAggregateDto);

  }
}
