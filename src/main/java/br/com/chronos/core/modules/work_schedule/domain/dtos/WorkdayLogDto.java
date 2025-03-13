package br.com.chronos.core.modules.work_schedule.domain.dtos;

import java.time.LocalDate;

public class WorkdayLogDto {
  public String id;
  public LocalDate date;
  public TimePunchDto timePunchSchedule;
  public TimePunchDto timePunchAdjustment;
  public TimePunchDto timePunchLog;
  public String status;

  public WorkdayLogDto setId(String id) {
    this.id = id;
    return this;
  }

  public WorkdayLogDto setDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public WorkdayLogDto setTimePunchSchedule(TimePunchDto timePunch) {
    this.timePunchSchedule = timePunch;
    return this;
  }

  public WorkdayLogDto setTimePunchLog(TimePunchDto timePunch) {
    this.timePunchLog = timePunch;
    return this;
  }

  public WorkdayLogDto setStatus(String status) {
    this.status = status;
    return this;
  }
}
