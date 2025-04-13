package br.com.chronos.core.global.domain.records;

import java.time.LocalDate;

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
}
