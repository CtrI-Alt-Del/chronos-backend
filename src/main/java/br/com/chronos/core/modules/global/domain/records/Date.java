package br.com.chronos.core.modules.global.domain.records;

import java.time.LocalDate;

public record Date(LocalDate value) {
  public static Date create(LocalDate value, String zoneArea) {
    return new Date(value);
  }

  public static Date create(LocalDate value) {
    return new Date(value);
  }

  public static Date createFromNow() {
    return new Date(LocalDate.now());
  }

  public Logical isEqualOrAfter(Date date) {
    return Logical.create(value.isEqual(date.value()) || value.isAfter(value));
  }

  public Logical isEqualOrBefore(Date date) {
    return Logical.create(value.isEqual(date.value()) || value.isBefore(value));
  }
}
