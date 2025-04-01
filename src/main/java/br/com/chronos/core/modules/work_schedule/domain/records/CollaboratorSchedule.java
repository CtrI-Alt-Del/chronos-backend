package br.com.chronos.core.modules.work_schedule.domain.records;

import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.modules.work_schedule.domain.entities.WeekdaySchedule;

public record CollaboratorSchedule(
    Id collaboratorId,
    Array<WeekdaySchedule> weekSchedule,
    DayOffSchedule daysOffSchedule) {

  public static CollaboratorSchedule create(
      String collaboratorId,
      List<WeekdayScheduleDto> weekdaySchedulesDto,
      DayOffScheduleDto dayOffScheduleDto) {
    var weekSchedule = Array.createFrom(weekdaySchedulesDto, WeekdaySchedule::new);
    var dayOffSchedule = new DayOffSchedule(dayOffScheduleDto);

    return new CollaboratorSchedule(Id.create(collaboratorId), weekSchedule, dayOffSchedule);
  }

  public static CollaboratorSchedule create(
      Id collaboratorId,
      Array<WeekdaySchedule> weekSchedule,
      DayOffSchedule dayOffSchedule) {
    return new CollaboratorSchedule(collaboratorId, weekSchedule, dayOffSchedule);
  }

  public WorkdayStatus getTodayWorkdayStatus() {
    if (daysOffSchedule.isTodayDayOff().isTrue()) {
      return WorkdayStatus.createAsDayOff();
    }
    return WorkdayStatus.createAsNormalDay();
  }

  public TimePunch getTodayTimePunchSchedule() {
    var today = Date.createFromNow();
    var todaySchedule = weekSchedule.find(
        (weekdaySchedule) -> {
          return weekdaySchedule.getWeekday().isEqual(today.getWeekday()).isTrue();
        });
    return todaySchedule.getTimePunch();
  }
}
