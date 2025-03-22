package br.com.chronos.core.modules.work_schedule.domain.entities.fakers;

import com.github.javafaker.Faker;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.fakers.DateFaker;
import br.com.chronos.core.modules.global.domain.records.fakers.IdFaker;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;

public class WorkScheduleFaker {
  private static Faker faker = new Faker();

  public static WorkSchedule fake() {
    return new WorkSchedule(fakeDto());
  }

  public static WorkScheduleDto fakeDto() {
    return new WorkScheduleDto()
        .setId(IdFaker.fakeDto())
        .setDescription(faker.name().title())
        .setWorkdaysCount((int) faker.number().randomNumber())
        .setDaysOffCount((int) faker.number().randomNumber())
        .setWeekSchedule(WeekdayScheduleFaker.fakeManyDtos(20).list())
        .setDaysOff(DateFaker.fakeManyDtos(10).list());
  }

  public static Array<WorkSchedule> fakeMany(int count) {
    Array<WorkSchedule> fakeWorkSchedules = Array.createAsEmpty();
    for (var index = 0; index < count; index++) {
      fakeWorkSchedules.add(fake());
    }
    return fakeWorkSchedules;
  }
}
