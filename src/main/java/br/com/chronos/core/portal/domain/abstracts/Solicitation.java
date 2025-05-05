package br.com.chronos.core.portal.domain.abstracts;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.exceptions.NotManagerException;
import br.com.chronos.core.global.domain.exceptions.NotSameCollaborationSector;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.portal.domain.dtos.SolicitationDto;
import br.com.chronos.core.portal.domain.exceptions.SolicitationAlreadyApprovedException;
import br.com.chronos.core.portal.domain.exceptions.SolicitationAlreadyDeniedException;
import br.com.chronos.core.portal.domain.records.SolicitationStatus;
import br.com.chronos.core.portal.domain.records.SolicitationType;

public abstract class Solicitation extends Entity {
  protected Text description;
  protected Date date;
  protected Text feedbackMessage;
  protected SolicitationStatus status;
  protected ResponsibleAggregate senderResponsible;
  protected ResponsibleAggregate replierResponsible;
  protected SolicitationType type;

  public Solicitation(SolicitationDto dto) {
    super(dto.id);
    description = dto.description != null ? Text.create(dto.description, "Descrição da solicitação") : null;
    feedbackMessage = dto.feedbackMessage != null
        ? Text.create(dto.feedbackMessage, "Mensagem de feedback da solicitação")
        : null;
    status = SolicitationStatus.create(dto.status);
    date = dto.date != null ? Date.create(dto.date) : Date.createFromNow();
    senderResponsible = new ResponsibleAggregate(dto.senderResponsible);
    replierResponsible = dto.replierResponsible != null ? new ResponsibleAggregate(dto.replierResponsible) : null;
  }

  public void approve(ResponsibleAggregate replierResponsible, Text feedbackMessage) {
    System.out.println(senderResponsible.getEntity());
    reply(replierResponsible, feedbackMessage);
    this.status = status.approve();
  }

  public void deny(ResponsibleAggregate replierResponsible, Text feedbackMessage) {
    reply(replierResponsible, feedbackMessage);
    this.status = status.deny();
  }

  private void reply(ResponsibleAggregate replierResponsible, Text feedbackMessage) {
    if (replierResponsible.getEntity().getRole().isManager().isFalse()) {
      throw new NotManagerException();
    }
    if (replierResponsible.getEntity().getSector()
        .isEqual(senderResponsible.getEntity().getSector()).isFalse()) {
      throw new NotSameCollaborationSector();
    }
    if (status.isApproved().isTrue()) {
      throw new SolicitationAlreadyApprovedException();
    }
    if (status.isDenied().isTrue()) {
      throw new SolicitationAlreadyDeniedException();
    }
    this.replierResponsible = replierResponsible;
    this.feedbackMessage = feedbackMessage;
  }

  public Text getDescription() {
    return description;
  }

  public SolicitationStatus getStatus() {
    return status;
  }

  public void setStatus(SolicitationStatus status) {
    this.status = status;
  }

  public Date getDate() {
    return date;
  }

  public Text getFeedbackMessage() {
    return feedbackMessage;
  }

  public void setFeedbackMessage(Text feedbackMessage) {
    this.feedbackMessage = feedbackMessage;
  }

  public ResponsibleAggregate getSenderResponsible() {
    return senderResponsible;
  }

  public ResponsibleAggregate getReplierResponsible() {
    return replierResponsible;
  }

  public void setReplierResponsible(ResponsibleAggregate replierResponsible) {
    this.replierResponsible = replierResponsible;
  }

  public SolicitationType getType() {
    return type;
  }

  public SolicitationDto getDto() {
    var dto = new SolicitationDto()
        .setId(getId().toString())
        .setStatus(getStatus().toString())
        .setDate(getDate().value())
        .setStatus(getStatus().toString())
        .setSenderResponsible(getSenderResponsible().getDto())
        .setType(getType().toString());

    if (getDescription() != null) {
      dto.setDescription(getDescription().value());
    }
    if (getFeedbackMessage() != null) {
      dto.setFeedbackMessage(getFeedbackMessage().value());
    }
    if (getReplierResponsible() != null) {
      dto.setReplierResponsible(getReplierResponsible().getDto());
    }
    return dto;
  }
}
