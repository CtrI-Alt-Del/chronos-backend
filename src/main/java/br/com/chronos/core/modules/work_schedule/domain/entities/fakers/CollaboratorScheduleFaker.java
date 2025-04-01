package br.com.chronos.core.modules.work_schedule.domain.entities.fakers;

import java.util.List;

import com.github.javafaker.Faker;

import br.com.chronos.core.modules.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.fakers.DaysOffScheduleFaker;

public class CollaboratorScheduleFaker {
  private static Faker faker = new Faker();

  public static CollaboratorSchedule fake(String collaboratorId) {
    var fakeWeekScheduleDto = List.of(
        WeekdayScheduleFaker.fakeDto().setWeekday("sunday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("monday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("tuesday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("wednesday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("thursday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("friday"),
        WeekdayScheduleFaker.fakeDto().setWeekday("saturday"));

    var schedules = List.of(
        List.of(5, 2),
        List.of(6, 1),
        List.of(4, 2),
        List.of(6, 2));
    var schedule = faker.options().nextElement(schedules);
    var workdaysCount = schedule.get(0);
    var daysOffCount = schedule.get(1);
    var fakeDaysOffDto = DaysOffScheduleFaker.fakeDto(workdaysCount, daysOffCount);
    var fakeDayOffScheduleDto = new DayOffScheduleDto()
        .setWorkdaysCount(workdaysCount)
        .setDaysOffCount(daysOffCount)
        .setDaysOff(fakeDaysOffDto);

    return CollaboratorSchedule.create(collaboratorId, fakeWeekScheduleDto, fakeDayOffScheduleDto);
  }

}
