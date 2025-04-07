package br.com.chronos.core.work_schedule.domain.dtos;

import java.util.ArrayList;
import java.util.List;

public class WeekScheduleDto {
  public String id;
  public List<WeekdayScheduleDto> weekdays = new ArrayList<>();

  public WeekScheduleDto setId(String id) {
    this.id = id;
    return this;
  }

  public WeekScheduleDto setWeekdays(List<WeekdayScheduleDto> weekdays) {
    this.weekdays = weekdays;
    return this;
  }
}
