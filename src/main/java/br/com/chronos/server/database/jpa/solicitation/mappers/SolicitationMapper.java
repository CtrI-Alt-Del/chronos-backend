package br.com.chronos.server.database.jpa.solicitation.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.solicitation.models.DayOffScheduleAdjustmentSolicitationModel;
import br.com.chronos.server.database.jpa.solicitation.models.SolicitationModel;
import br.com.chronos.server.database.jpa.solicitation.models.TimePunchLogAdjustmentSolicitationModel;

@Component
public class SolicitationMapper {

    @Autowired
    private DayOffScheduleAdjustmentSolicitationMapper dayOffScheduleAdjustmentSolicitationMapper;
    
    @Autowired
    private TimePunchLogAdjustmentSolicitationMapper timePunchLogAdjustmentSolicitationMapper;

    // To handle the conversion from entity (model) to domain entity
    public Solicitation toEntity(SolicitationModel model) {
        if (model instanceof DayOffScheduleAdjustmentSolicitationModel) {
            return dayOffScheduleAdjustmentSolicitationMapper.toEntity((DayOffScheduleAdjustmentSolicitationModel) model);
        } else if (model instanceof TimePunchLogAdjustmentSolicitationModel) {
            return timePunchLogAdjustmentSolicitationMapper.toEntity((TimePunchLogAdjustmentSolicitationModel) model);
        }
        throw new IllegalArgumentException("Unknown solicitation type: " + model.getClass());
    }

    // To handle the conversion from domain entity to model
    public SolicitationModel toModel(Solicitation entity) {
        if (entity instanceof DayOffScheduleAdjustmentSolicitation) {
            return dayOffScheduleAdjustmentSolicitationMapper.toModel((DayOffScheduleAdjustmentSolicitation) entity);
        } else if (entity instanceof TimePunchLogAdjustmentSolicitation) {
            return timePunchLogAdjustmentSolicitationMapper.toModel((TimePunchLogAdjustmentSolicitation) entity);
        }
        throw new IllegalArgumentException("Unknown solicitation type: " + entity.getClass());
    }

    // Common logic for mapping the common fields
    protected void mapCommonFields(SolicitationModel model, Solicitation entity) {
        var senderResponsible = CollaboratorModel.builder()
            .id(entity.getSenderResponsible().getId().value()).build();
        var replierResponsible = (entity.getReplierResponsible() != null)
            ? CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build()
            : null;

        model.setId(entity.getId().value());
        model.setDescription(entity.getDescription() != null ? entity.getDescription().value() : null);
        model.setRequestedAt(entity.getDate().value());
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
            .setRole(model.getSenderResponsible().getAccount().getRole().toString());

        ResponsibleAggregateDto senderResponsibleAggregateDto = new ResponsibleAggregateDto(senderResponsibleDto);

        ResponsibleAggregateDto replierResponsibleAggregateDto = null;
        if (model.getReplierResponsible() != null) {
            ResponsibleDto replierResponsibleDto = new ResponsibleDto()
                .setId(model.getReplierResponsible().getId().toString())
                .setName(model.getReplierResponsible().getName())
                .setEmail(model.getReplierResponsible().getAccount().getEmail())
                .setRole(model.getReplierResponsible().getAccount().getRole().toString());

            replierResponsibleAggregateDto = new ResponsibleAggregateDto(replierResponsibleDto);
        }

        return new SolicitationDto()
            .setId(model.getId().toString())
            .setDescription(model.getDescription())
            .setDate(model.getRequestedAt())
            .setStatus(model.getSolicitationStatus().toString())
            .setFeedbackMessage(model.getFeedbackMessage())
            .setSenderResponsible(senderResponsibleAggregateDto)
            .setReplierResponsible(replierResponsibleAggregateDto);
    }
}

