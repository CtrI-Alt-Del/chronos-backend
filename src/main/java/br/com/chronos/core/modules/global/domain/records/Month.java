package br.com.chronos.core.modules.global.domain.records;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public record Month(Array<Date> days) {
  public static Month createFromNow() {
    YearMonth currentMonth = YearMonth.now();
    int daysInMonth = currentMonth.lengthOfMonth();
    List<Date> days = new ArrayList<>();

    for (int day = 1; day <= daysInMonth; day++) {
      days.add(Date.create(currentMonth.atDay(day)));
    }

    return new Month(Array.create(days));
  }

  public Date firstMonday() {
    var firstDay = days.firstItem();

    while (firstDay.isMonday().isFalse()) {
      firstDay = firstDay.plusDays(1);
    }

    return firstDay;
  }

  public Date lastDay() {
    return days.lastItem();
  }
}
