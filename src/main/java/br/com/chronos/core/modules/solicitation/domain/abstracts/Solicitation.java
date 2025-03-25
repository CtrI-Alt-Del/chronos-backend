package br.com.chronos.core.modules.solicitation.domain.abstracts;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Text;
import br.com.chronos.core.modules.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationStatus;

public abstract class Solicitation extends Entity {
  public Text description;
  public Date date;
  public Text feedbackMessage;
  public SolicitationStatus status;
  public ResponsibleAggregate senderResponsible;
  public ResponsibleAggregate replierResponsible;

  public Solicitation(SolicitationDto dto) {
    description = Text.create(dto.description, "Descrição da solicitação");
    feedbackMessage = Text.create(dto.feedbackMessage, "Mensagem de feedback da solicitação");
    status = SolicitationStatus.create(dto.status);
    date = Date.create(dto.date);
    senderResponsible = new ResponsibleAggregate(dto.senderResponsible);
    replierResponsible = new ResponsibleAggregate(dto.replierResponsible);
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

  public SolicitationDto getDto() {
    return new SolicitationDto()
        .setDescription(description.value())
        .setStatus(getStatus().toString())
        .setFeedbackMessage(getFeedbackMessage().value())
        .setDate(getDate().value())
        .setStatus(getStatus().toString())
        .setReplierResponsible(getReplierResponsible().getDto())
        .setSenderResponsible(getSenderResponsible().getDto());
  }
}
