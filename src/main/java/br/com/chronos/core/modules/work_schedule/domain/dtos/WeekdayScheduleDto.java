package br.com.chronos.core.modules.work_schedule.domain.dtos;

public class WeekdayScheduleDto {
  public String id;
  public String weekday;
  public TimePunchDto timePunch;

  public WeekdayScheduleDto setId(String id) {
    this.id = id;
    return this;
  }

  public WeekdayScheduleDto setWeekday(String weekday) {
    this.weekday = weekday;
    return this;
  }

  public WeekdayScheduleDto setTimePunch(TimePunchDto timePunch) {
    this.timePunch = timePunch;
    return this;
  }
}
