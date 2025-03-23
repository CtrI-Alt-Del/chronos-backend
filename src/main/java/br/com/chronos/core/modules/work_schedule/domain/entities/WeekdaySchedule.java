package br.com.chronos.core.modules.work_schedule.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.records.Weekday;

public final class WeekdaySchedule extends Entity {
  private Weekday weekday;
  private TimePunch timePunch;

  public WeekdaySchedule(WeekdayScheduleDto dto) {
    super(dto.id);
    weekday = Weekday.create(dto.weekday);
    timePunch = new TimePunch(dto.timePunch);
  }

  public Weekday getWeekday() {
    return weekday;
  }

  public TimePunch getTimePunch() {
    return timePunch;
  }

  public WeekdayScheduleDto getDto() {
    return new WeekdayScheduleDto()
        .setId(getId().toString())
        .setWeekday(getWeekday().toString().toLowerCase())
        .setTimePunch(getTimePunch().getDto());
  }

}
