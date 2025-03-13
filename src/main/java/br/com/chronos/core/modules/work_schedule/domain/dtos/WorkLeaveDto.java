package br.com.chronos.core.modules.work_schedule.domain.dtos;

import java.time.LocalDate;

public class WorkLeaveDto {
  public String id;
  public LocalDate startedAt;
  public LocalDate endedAt;

  public WorkLeaveDto setId(String id) {
    this.id = id;
    return this;
  }

  public WorkLeaveDto setStartedAt(LocalDate startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public WorkLeaveDto setEndedAt(LocalDate endedAt) {
    this.endedAt = endedAt;
    return this;
  }
}
