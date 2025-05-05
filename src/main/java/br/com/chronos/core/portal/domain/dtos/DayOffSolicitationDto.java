package br.com.chronos.core.portal.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class DayOffSolicitationDto extends SolicitationDto {
  public LocalDate dayOff;
  public int workload;

  public DayOffSolicitationDto setDayOff(LocalDate dayOff) {
    this.dayOff = dayOff;
    return this;
  }

  public DayOffSolicitationDto setWorkload(int workload) {
    this.workload = workload;
    return this;
  }

  public DayOffSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }

  public DayOffSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }

  public DayOffSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }

  public DayOffSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }

  public DayOffSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }

  public DayOffSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }

  public DayOffSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }
}
