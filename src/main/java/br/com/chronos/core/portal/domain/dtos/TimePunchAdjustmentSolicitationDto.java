package br.com.chronos.core.portal.domain.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class TimePunchAdjustmentSolicitationDto extends SolicitationDto {
  public LocalTime time;
  public String period;
  public LocalDate workdayLogDate;
  public String reason;

  public TimePunchAdjustmentSolicitationDto setTime(LocalTime time) {
    this.time = time;
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setPeriod(String period) {
    this.period = period;
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setWorkdayLogDate(LocalDate workdayLogDate) {
    this.workdayLogDate = workdayLogDate;
    return this;
  }

  public TimePunchAdjustmentSolicitationDto setReason(String reason) {
    this.reason = reason;
    return this;
  }
}
