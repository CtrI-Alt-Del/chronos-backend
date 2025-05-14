package br.com.chronos.core.portal.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class SolicitationDto {
  public String id;
  public String description;
  public LocalDate date;
  public String status;
  public String feedbackMessage;
  public ResponsibleAggregateDto senderResponsible;
  public ResponsibleAggregateDto replierResponsible;
  public String type;

  public SolicitationDto setType(String type) {
    this.type = type;
    return this;
  }

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
    System.out.println("senderResponsible: " + this.senderResponsible.entity.id);
    return this;
  }

  public SolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    this.replierResponsible = replierResponsible;
    return this;
  }
}
