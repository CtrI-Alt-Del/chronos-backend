package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalTime;

public class WorkTimeLogDto {
  public LocalTime workdayTime;
  public LocalTime workMonthTime;

  public WorkTimeLogDto setWorkdayTime(LocalTime workdayTime) {
    this.workdayTime = workdayTime;
    return this;
  }

  public WorkTimeLogDto setWorkMonthTime(LocalTime workMonthTime) {
    this.workMonthTime = workMonthTime;
    return this;
  }
}
