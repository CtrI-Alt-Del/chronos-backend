package br.com.chronos.core.solicitation.domain.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class TimePunchLogAdjustmentSolicitationDto extends SolicitationDto {
  public LocalTime time;
  public String period;
  public LocalDate workdayLogDate;
  public String reason;

  public TimePunchLogAdjustmentSolicitationDto setTime(LocalTime time) {
    this.time = time;
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setPeriod(String period) {
    this.period = period;
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setWorkdayLogDate(LocalDate workdayLogDate) {
    this.workdayLogDate = workdayLogDate;
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setReason(String reason) {
    this.reason = reason;
    return this;
  }
}
