package br.com.chronos.core.global.domain.records;

import java.time.LocalDate;
import java.time.YearMonth;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record DateRange(Date startDate, Date endDate) {
  public static DateRange create(LocalDate startDate, LocalDate endDate, int defaultRange) {
    var dateRangeStart = Date.create(startDate);
    var dateRangeEnd = Date.create(endDate);

    if (startDate == null || endDate == null) {
      dateRangeEnd = Date.createFromNow();
      dateRangeStart = dateRangeEnd.minusDays(defaultRange);
    }

    if (dateRangeStart.isAfter(dateRangeEnd).isTrue()) {
      throw new ValidationException("data inicial", "não pode ser maior que a data final");
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

  public Logical covers(Date date) {
    return date.isEqualOrAfter(startDate).and(date.isEqualOrBefore(endDate));
  }

  public Array<Date> getDates() {
    Array<Date> dates = Array.createAsEmpty();
    for (var date = startDate; date.isBefore(endDate).isTrue(); date = date.plusDays(1)) {
      dates.add(date);
    }
    return dates;
  }
}
