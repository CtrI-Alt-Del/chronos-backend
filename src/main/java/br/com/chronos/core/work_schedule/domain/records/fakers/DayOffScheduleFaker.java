package br.com.chronos.core.work_schedule.domain.records.fakers;

import java.util.List;
import com.github.javafaker.Faker;

import br.com.chronos.core.global.domain.records.fakers.IdFaker;
import br.com.chronos.core.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;

public class DayOffScheduleFaker {
  private static Faker faker = new Faker();

  public static DayOffSchedule fake() {
    var schedules = List.of(
        List.of(5, 2),
        List.of(6, 1),
        List.of(4, 2),
        List.of(6, 2));
    var schedule = faker.options().nextElement(schedules);
    var workdaysCount = schedule.get(0);
    var daysOffCount = schedule.get(1);

    return new DayOffSchedule(fakeDto(workdaysCount, daysOffCount));
  }

  public static DayOffScheduleDto fakeDto(int workdaysCount, int daysOffCount) {
    var daysOff = DayOffSchedule.scheduleDaysOff(workdaysCount, daysOffCount);
    return new DayOffScheduleDto()
        .setId(IdFaker.fakeDto())
        .setWorkdaysCount(workdaysCount)
        .setDaysOffCount(daysOffCount)
        .setDaysOff(daysOff.map(dayOff -> dayOff.value()).list())
        .setCollaboratorId(faker.idNumber().valid());
  }
}
