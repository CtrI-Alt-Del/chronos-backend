package br.com.chronos.core.global.domain.records;

import java.time.LocalDateTime;

public record DateTime(LocalDateTime value) {
  public static DateTime create(LocalDateTime value) {
    if (value instanceof LocalDateTime) {
      return new DateTime(value);
    }
    return DateTime.createFromNow();
  }

  public static DateTime createFromNow() {
    return new DateTime(LocalDateTime.now());
  }

  public DateTime plusHours(int hours) {
    return new DateTime(value.plusHours(hours));
  }

  public DateTime plusDays(int days) {
    return new DateTime(value.plusDays(days));
  }

  public DateTime minusDays(int days) {
    return new DateTime(value.minusDays(days));
  }
}
