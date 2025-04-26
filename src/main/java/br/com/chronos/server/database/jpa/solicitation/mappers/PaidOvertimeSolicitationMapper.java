package br.com.chronos.server.database.jpa.solicitation.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.solicitation.domain.entities.PaidOvertimeSolicitation;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.solicitation.models.PaidOvertimeSolicitationModel;

@Component
public class PaidOvertimeSolicitationMapper {
  public PaidOvertimeSolicitationModel toModel(PaidOvertimeSolicitation entity) {
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

    return PaidOvertimeSolicitationModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription() != null ? entity.getDescription().value() : null)
        .requestedAt(entity.getDate().value())
        .feedbackMessage(entity.getFeedbackMessage() != null ? entity.getFeedbackMessage().value() : null)
        .solicitationStatus(entity.getStatus().value())
        .senderResponsible(senderResponse)
        .replierResponsible(replierResponse)
        .build();

  }
}
