package br.com.chronos.core.work_schedule.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Count;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Month;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.work_schedule.domain.exceptions.ZeroDaysOffCountException;
import br.com.chronos.core.work_schedule.domain.exceptions.ZeroWorkdaysCountException;

public class DayOffSchedule extends Entity {
  private Count workdaysCount;
  private Count daysOffCount;
  private Array<Date> daysOff;

  public DayOffSchedule(DayOffScheduleDto dto) {
    super(dto.id);

    if (dto.workdaysCount == 0) {
      throw new ZeroWorkdaysCountException();
    }
    if (dto.daysOffCount == 0) {
      throw new ZeroDaysOffCountException();
    }

    workdaysCount = Count.create(dto.workdaysCount, "Contagem de dias de trabalho");
    daysOffCount = Count.create(dto.daysOffCount, "Contagem de folgas");
    daysOff = Array.createFrom(dto.daysOff, Date::create);
  }

  public static Array<Date> scheduleDaysOff(int workdaysCountValue, int daysOffCountValue) {
    var workdaysCountInteger = PlusIntegerNumber.create(
        workdaysCountValue,
        "contagem de dias de trabalho");
    var daysOffCountInteger = PlusIntegerNumber.create(
        daysOffCountValue,
        "contagem de dias de folga");

    if (workdaysCountInteger.value() == 0) {
      throw new ZeroWorkdaysCountException();
    }
    if (daysOffCountInteger.value() == 0) {
      throw new ZeroDaysOffCountException();
    }

    var month = Month.createFromNow();
    var monthDay = month.firstMonday();

    if (monthDay.value().getDayOfMonth() != 1) {
      monthDay = monthDay.minusDays(7);
    }

    var monthLastDay = month.lastDay();
    var workdaysCount = Count.create();
    var daysOffCount = Count.create();
    Array<Date> daysOff = Array.createAsEmpty();

    while (monthDay.isEqual(monthLastDay).isFalse()) {
      if (workdaysCount.isEqual(workdaysCountValue).isTrue()) {
        daysOffCount = daysOffCount.increment();
        daysOff = daysOff.add(monthDay);

        if (daysOffCount.isEqual(daysOffCountValue).isTrue()) {
          workdaysCount = workdaysCount.reset();
          daysOffCount = daysOffCount.reset();
        }
      } else {
        workdaysCount = workdaysCount.increment();
      }

      monthDay = monthDay.plusDays(1);
    }

    return daysOff;
  }

  public Logical isTodayDayOff() {
    var today = Date.createFromNow();

    for (var day : daysOff.list()) {
      var isDayOff = day.isEqual(today);
      if (isDayOff.isTrue())
        return Logical.createAsTrue();
    }

    return Logical.createAsFalse();
  }

  public void resetDaysOffSchedule() {
    daysOff = scheduleDaysOff(workdaysCount.integer().value(), daysOffCount.integer().value());
  }

  public Count getWorkdaysCount() {
    return workdaysCount;
  }

  public Count getDaysOffCount() {
    return daysOffCount;
  }

  public Array<Date> getDaysOff() {
    return daysOff;
  }

  public DayOffScheduleDto getDto() {
    return new DayOffScheduleDto()
        .setId(getId().toString())
        .setWorkdaysCount(getWorkdaysCount().integer().value())
        .setDaysOffCount(getDaysOffCount().integer().value())
        .setDaysOff(getDaysOff().map((dayOff) -> dayOff.value()).list());
  }
}
