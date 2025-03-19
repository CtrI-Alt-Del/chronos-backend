package br.com.chronos.core.modules.work_schedule.domain.records;

import br.com.chronos.core.modules.global.domain.records.Count;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.Month;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.ZeroDaysOffCountException;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.ZeroWorkdaysCountException;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Array;

public record DaysOffSchedule(Array<Date> days) {
  public static DaysOffSchedule create(List<LocalDate> daysOff) {
    return new DaysOffSchedule(Array.createFrom(daysOff, Date::create));
  }

  public static DaysOffSchedule create(int workdaysCountValue, int daysOffCountValue) {
    if (workdaysCountValue == 0) {
      throw new ZeroWorkdaysCountException();
    }
    if (daysOffCountValue == 0) {
      throw new ZeroDaysOffCountException();
    }

    var month = Month.createFromNow();
    var monthDay = month.firstMonday();
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

    return new DaysOffSchedule(daysOff);
  }

  public Logical isTodayDayOff() {
    var today = Date.createFromNow();

    for (var day : days.list()) {
      var isDayOff = day.isEqual(today);
      if (isDayOff.isTrue())
        return Logical.createAsTrue();
    }

    return Logical.createAsFalse();
  }
}
