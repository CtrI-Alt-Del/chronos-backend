package br.com.chronos.core.modules.work_schedule.domain.entities.fakers;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.fakers.IdFaker;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WeekdaySchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.fakers.WeekdayFaker;

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

  public static Array<WeekdayScheduleDto> fakeManyDtos(int count) {
    Array<WeekdayScheduleDto> fakeWeekScheduleDtos = Array.createAsEmpty();
    for (var index = 0; index < count; index++) {
      fakeWeekScheduleDtos.add(fakeDto());
    }
    return fakeWeekScheduleDtos;
  }
}
