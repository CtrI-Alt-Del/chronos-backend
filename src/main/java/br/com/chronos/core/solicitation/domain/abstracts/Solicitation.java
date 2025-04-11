package br.com.chronos.core.solicitation.domain.abstracts;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.solicitation.domain.records.SolicitationStatus;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;

public abstract class Solicitation extends Entity {
  public Text description;
  public Date date;
  public Text feedbackMessage;
  public SolicitationStatus status;
  public ResponsibleAggregate senderResponsible;
  public ResponsibleAggregate replierResponsible;
  public SolicitationType type;

  public Solicitation(SolicitationDto dto) {
    super(dto.id);
    description = dto.description != null ? Text.create(dto.description, "Descrição da solicitação") : null;
    feedbackMessage = dto.feedbackMessage != null
        ? Text.create(dto.feedbackMessage, "Mensagem de feedback da solicitação")
        : null;
    status = SolicitationStatus.create(dto.status);
    date = dto.date != null ? Date.create(dto.date) : Date.now();
    senderResponsible = new ResponsibleAggregate(dto.senderResponsible);
    replierResponsible = dto.replierResponsible != null ? new ResponsibleAggregate(dto.replierResponsible) : null;
  }

  public Text getDescription() {
    return description;
  }

  public SolicitationStatus getStatus() {
    return status;
  }

  public Date getDate() {
    return date;
  }

  public Text getFeedbackMessage() {
    return feedbackMessage;
  }

  public ResponsibleAggregate getSenderResponsible() {
    return senderResponsible;
  }

  public ResponsibleAggregate getReplierResponsible() {
    return replierResponsible;
  }

  public SolicitationType getType() {
    return type;
  }

  public SolicitationDto getDto() {
    return new SolicitationDto()
        .setId(getId().toString())
        .setDescription(getDescription().value())
        .setStatus(getStatus().toString())
        .setFeedbackMessage(getFeedbackMessage().value())
        .setDate(getDate().value())
        .setStatus(getStatus().value().toString())
        .setReplierResponsible(getReplierResponsible().getDto())
        .setSenderResponsible(getSenderResponsible().getDto())
        .setType(getType().toString());
  }
}
