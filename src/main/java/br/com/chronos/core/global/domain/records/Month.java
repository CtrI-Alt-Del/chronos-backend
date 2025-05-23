package br.com.chronos.core.global.domain.records;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public record Month(Array<Date> days) {
  public static Month create(int year, int month) {
    YearMonth yearMonth = YearMonth.of(year, month);
    int daysInMonth = yearMonth.lengthOfMonth();
    List<Date> days = new ArrayList<>();

    for (int day = 1; day <= daysInMonth; day++) {
      days.add(Date.create(yearMonth.atDay(day)));
    }

    return new Month(Array.create(days));
  }

  public static Month createFromNow() {
    YearMonth currentMonth = YearMonth.now();
    int daysInMonth = currentMonth.lengthOfMonth();
    List<Date> days = new ArrayList<>();

    for (int day = 1; day <= daysInMonth; day++) {
      days.add(Date.create(currentMonth.atDay(day)));
    }

    return new Month(Array.create(days));
  }

  public Date firstDay() {
    return days.firstItem();
  }

  public Date firstMonday() {
    var firstDay = firstDay();

    while (firstDay.isMonday().isFalse()) {
      firstDay = firstDay.plusDays(1);
    }

    return firstDay;
  }

  public Date lastDay() {
    return days.lastItem();
  }
}
