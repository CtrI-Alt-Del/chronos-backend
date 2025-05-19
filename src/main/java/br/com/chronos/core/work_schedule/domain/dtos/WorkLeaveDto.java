package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalDate;

public class WorkLeaveDto {
  public LocalDate startedAt;
  public LocalDate endedAt;

  public WorkLeaveDto setStartedAt(LocalDate startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public WorkLeaveDto setEndedAt(LocalDate endedAt) {
    this.endedAt = endedAt;
    return this;
  }
}
