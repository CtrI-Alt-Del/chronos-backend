package br.com.chronos.core.portal.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class WorkLeaveSolicitationDto extends SolicitationDto {
  public LocalDate startedAt;
  public LocalDate endedAt;
  public Boolean isVacation;
  public JustificationDto justification;

  public WorkLeaveSolicitationDto setStartedAt(LocalDate startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public WorkLeaveSolicitationDto setEndedAt(LocalDate endedAt) {
    this.endedAt = endedAt;
    return this;
  }

  public WorkLeaveSolicitationDto setIsVacation(Boolean isVacation) {
    this.isVacation = isVacation;
    return this;
  }

  public WorkLeaveSolicitationDto setJustification(JustificationDto justification) {
    this.justification = justification;
    return this;
  }

  public WorkLeaveSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }

  public WorkLeaveSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }

  public WorkLeaveSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }

  public WorkLeaveSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }

  public WorkLeaveSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }

  public WorkLeaveSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }

  public WorkLeaveSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }
}
