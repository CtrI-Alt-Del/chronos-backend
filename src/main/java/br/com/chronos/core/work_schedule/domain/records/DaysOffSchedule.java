package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.records.Count;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Month;
import br.com.chronos.core.global.domain.records.PlusInteger;
import br.com.chronos.core.work_schedule.domain.exceptions.ZeroDaysOffCountException;
import br.com.chronos.core.work_schedule.domain.exceptions.ZeroWorkdaysCountException;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.global.domain.records.Array;

public record DaysOffSchedule(Array<Date> days) {
  public static DaysOffSchedule create(List<LocalDate> daysOff) {
    return new DaysOffSchedule(Array.createFrom(daysOff, Date::create));
  }

  public static DaysOffSchedule create(int workdaysCountValue, int daysOffCountValue) {
    var workdaysCountInteger = PlusInteger.create(
        workdaysCountValue,
        "contagem de dias de trabalho");
    var daysOffCountInteger = PlusInteger.create(
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
