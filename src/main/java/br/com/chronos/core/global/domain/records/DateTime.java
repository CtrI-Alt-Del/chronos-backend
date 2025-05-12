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

  public DateTime plusMinutes(int minutes) {
    return new DateTime(value.plusMinutes(minutes));
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

  public DateTime minusMinutes(int minutes) {
    return new DateTime(value.minusMinutes(minutes));
  }

  public DateTime minusHours(int hours) {
    return new DateTime(value.minusHours(hours));
  }

  public Logical isBefore(DateTime dateTime) {
    return Logical.create(value.isBefore(dateTime.value()));
  }

  public Logical isAfter(DateTime dateTime) {
    return Logical.create(value.isAfter(dateTime.value()));
  }
}
