package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalDate;
import java.util.List;

public class DayOffScheduleDto {
  public String id;
  public int workdaysCount;
  public int daysOffCount;
  public List<LocalDate> daysOff;

  public DayOffScheduleDto setId(String id) {
    this.id = id;
    return this;
  }

  public DayOffScheduleDto setWorkdaysCount(int workdaysCount) {
    this.workdaysCount = workdaysCount;
    return this;
  }

  public DayOffScheduleDto setDaysOffCount(int daysOffCount) {
    this.daysOffCount = daysOffCount;
    return this;
  }

  public DayOffScheduleDto setDaysOff(List<LocalDate> daysOff) {
    this.daysOff = daysOff;
    return this;
  }
}
