package br.com.chronos.core.modules.solicitation.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;

public class SolicitationDto {
  public String id;
  public String description;
  public LocalDate date;
  public String status;
  public String feedbackMessage;
  public ResponsibleAggregateDto senderResponsible;
  public ResponsibleAggregateDto replierResponsible;

  public SolicitationDto setId(String id) {
    this.id = id;
    return this;
  }

  public SolicitationDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public SolicitationDto setDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public SolicitationDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public SolicitationDto setFeedbackMessage(String feedbackMessage) {
    this.feedbackMessage = feedbackMessage;
    return this;
  }

  public SolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    this.senderResponsible = senderResponsible;
    return this;
  }

  public SolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    this.replierResponsible = replierResponsible;
    return this;
  }
}
