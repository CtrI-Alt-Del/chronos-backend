package br.com.chronos.core.global.domain.records;

import java.time.LocalDate;
import java.time.YearMonth;

public record DateRange(Date startDate, Date endDate) {
  public static DateRange create(LocalDate startDate, LocalDate endDate) {
    var dateRangeStart = Date.create(startDate);
    var dateRangeEnd = Date.create(endDate);

    if (startDate == null || endDate == null) {
      dateRangeEnd = Date.createFromNow();
      dateRangeStart = dateRangeEnd.minusDays(7);
    }

    return new DateRange(dateRangeStart, dateRangeEnd);
  }

  public static DateRange createAsCurrentMonthRange() {
    var today = LocalDate.now();
    var firstDayOfMonth = today.withDayOfMonth(1);
    var currentYearMonth = YearMonth.from(today);
    var lastDayOfMonth = currentYearMonth.atEndOfMonth();
    return new DateRange(Date.create(firstDayOfMonth), Date.create(lastDayOfMonth));
  }

  public static DateRange createAsMonthRange(int month, int year) {
    if (year == 0) {
      return createAsCurrentMonthRange();
    }

    var yearMonth = YearMonth.of(year, month);
    var firstDayOfMonth = yearMonth.atDay(1);
    var lastDayOfMonth = yearMonth.atEndOfMonth();
    return new DateRange(Date.create(firstDayOfMonth), Date.create(lastDayOfMonth));
  }

}
