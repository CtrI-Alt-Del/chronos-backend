package br.com.chronos.core.modules.work_schedule.domain.entities.fakers;

import java.util.List;

import com.github.javafaker.Faker;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.fakers.IdFaker;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.fakers.DaysOffScheduleFaker;

public class WorkScheduleFaker {
  private static Faker faker = new Faker();

  public static WorkSchedule fake() {
    return new WorkSchedule(fakeDto());
  }

  public static WorkScheduleDto fakeDto() {
    var schedules = List.of(List.of(5, 2), List.of(6, 1), List.of(4, 2), List.of(6, 2));
    var schedule = faker.options().nextElement(schedules);
    var workdaysCount = schedule.get(0);
    var daysOffCount = schedule.get(1);
    var fakeDaysOff = DaysOffScheduleFaker.fakeDto(workdaysCount, daysOffCount);

    var weekSchedule = List.of(
        WeekdayScheduleFaker.fakeDto().setWeekday("sunday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("monday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("tuesday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("wednesday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("thursday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("friday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("saturday"));

    return new WorkScheduleDto()
        .setId(IdFaker.fakeDto())
        .setDescription(faker.name().title())
        .setWorkdaysCount(workdaysCount)
        .setDaysOffCount(daysOffCount)
        .setDaysOff(fakeDaysOff)
        .setWeekSchedule(weekSchedule);
  }

  public static Array<WorkSchedule> fakeMany(int count) {
    Array<WorkSchedule> fakeWorkSchedules = Array.createAsEmpty();
    for (var index = 0; index < count; index++) {
      fakeWorkSchedules.add(fake());
    }
    return fakeWorkSchedules;
  }
}
