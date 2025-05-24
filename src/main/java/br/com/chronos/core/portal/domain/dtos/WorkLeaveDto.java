package br.com.chronos.core.portal.domain.dtos;

import java.time.LocalDate;

public class WorkLeaveDto {
  public LocalDate startedAt;
  public LocalDate endedAt;
  public Boolean isVacation;
  public String description;
  public JustificationDto justification;

  public WorkLeaveDto setStartedAt(LocalDate startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public WorkLeaveDto setEndedAt(LocalDate endedAt) {
    this.endedAt = endedAt;
    return this;
  }

  public WorkLeaveDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public WorkLeaveDto setIsVacation(Boolean isVacation) {
    this.isVacation = isVacation;
    return this;
  }

  public WorkLeaveDto setJustification(JustificationDto justification) {
    this.justification = justification;
    return this;
  }
}
