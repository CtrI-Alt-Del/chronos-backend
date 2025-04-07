package br.com.chronos.core.work_schedule.domain.entities.fakers;

import br.com.chronos.core.global.domain.records.fakers.IdFaker;
import br.com.chronos.core.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.work_schedule.domain.entities.WeekdaySchedule;
import br.com.chronos.core.work_schedule.domain.records.fakers.WeekdayFaker;

public class WeekdayScheduleFaker {
  public static WeekdaySchedule fake() {
    return new WeekdaySchedule(fakeDto());
  }

  public static WeekdayScheduleDto fakeDto() {
    return new WeekdayScheduleDto()
        .setId(IdFaker.fakeDto())
        .setTimePunch(TimePunchFaker.fakeDto())
        .setWeekday(WeekdayFaker.fakeDto());
  }
}
