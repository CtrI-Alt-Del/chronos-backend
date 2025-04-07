package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalDate;
import java.util.List;

public class WorkScheduleDto {
  public String id;
  public String description;
  public int workdaysCount;
  public int daysOffCount;
  public List<WeekdayScheduleDto> weekSchedule;
  public List<LocalDate> daysOff;

  public WorkScheduleDto setId(String id) {
    this.id = id;
    return this;
  }

  public WorkScheduleDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public WorkScheduleDto setWorkdaysCount(int workdaysCount) {
    this.workdaysCount = workdaysCount;
    return this;
  }

  public WorkScheduleDto setDaysOffCount(int daysOffCount) {
    this.daysOffCount = daysOffCount;
    return this;
  }

  public WorkScheduleDto setWeekSchedule(List<WeekdayScheduleDto> weekSchedule) {
    this.weekSchedule = weekSchedule;
    return this;
  }

  public WorkScheduleDto setDaysOff(List<LocalDate> daysOff) {
    this.daysOff = daysOff;
    return this;
  }
}
