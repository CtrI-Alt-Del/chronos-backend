package br.com.chronos.core.solicitation.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.work_schedule.domain.dtos.DayOffScheduleDto;

public class DayOffScheduleAdjustmentSolicitationDto extends SolicitationDto {
  public DayOffScheduleDto dayOffScheduleDto;

  public DayOffScheduleAdjustmentSolicitationDto setDayOffSchedule(DayOffScheduleDto dayOffScheduleDto) {
    this.dayOffScheduleDto = dayOffScheduleDto;
    return this;
  }

  public DayOffScheduleAdjustmentSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }

  public DayOffScheduleAdjustmentSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }

  public DayOffScheduleAdjustmentSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }

  public DayOffScheduleAdjustmentSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }

  public DayOffScheduleAdjustmentSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }

  public DayOffScheduleAdjustmentSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }

  public DayOffScheduleAdjustmentSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }
}
