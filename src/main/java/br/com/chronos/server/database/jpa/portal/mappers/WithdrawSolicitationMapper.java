
package br.com.chronos.server.database.jpa.portal.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.portal.domain.dtos.WithdrawSolicitationDto;
import br.com.chronos.core.portal.domain.entities.WithdrawSolicitation;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.portal.models.WithdrawSolicitationModel;

@Component
public class WithdrawSolicitationMapper {

  @Autowired
  private JustificationMapper justificationMapper;

  public WithdrawSolicitationModel toModel(WithdrawSolicitation entity) {
    var senderResponse = CollaboratorModel
        .builder()
        .id(entity.getSenderResponsible().getId().value())
        .build();

    var replierResponse = (entity.getReplierResponsible() != null)
        ? CollaboratorModel.builder().id(entity.getReplierResponsible().getId().value()).build()
        : null;
    var justification = entity.getJustification() != null
        ? justificationMapper.toModel(entity.getJustification())
        : null;
    var solicitationModel = WithdrawSolicitationModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription() != null ? entity.getDescription().value() : null)
        .date(entity.getDate().value())
        .feedbackMessage(entity.getFeedbackMessage() != null ? entity.getFeedbackMessage().value() : null)
        .solicitationStatus(entity.getStatus().value())
        .senderResponsible(senderResponse)
        .replierResponsible(replierResponse)
        .withdrawalDays(entity.getWithdrawalDays().map(date -> date.value()).list())
        .justification(justification)
        .build();
    return solicitationModel;
  }

  public WithdrawSolicitationDto toDto(WithdrawSolicitationModel model) {
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

    var justificationDto = model.getJustification() != null
        ? justificationMapper.toDto(model.getJustification())
        : null;
    return new WithdrawSolicitationDto()
        .setId(model.getId().toString())
        .setDescription(model.getDescription())
        .setDate(model.getDate())
        .setFeedbackMessage(model.getFeedbackMessage())
        .setStatus(model.getSolicitationStatus().toString())
        .setSenderResponsible(senderResponsibleAggregateDto)
        .setReplierResponsible(replierResponsibleAggregateDto)
        .setWithdrawalDays(model.getWithdrawalDays())
        .setJustification(justificationDto);
  }

  public WithdrawSolicitation toEntity(WithdrawSolicitationModel model) {
    return new WithdrawSolicitation(toDto(model));
  }

}
