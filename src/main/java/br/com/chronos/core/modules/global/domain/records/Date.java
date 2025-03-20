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

  public Date plusDays(int daysCount) {
    return new Date(value.plusDays(daysCount));
  }

  public Date minusDays(int daysCount) {
    return new Date(value.minusDays(daysCount));
  }

  public Logical isEqual(Date date) {
    return Logical.create(value.isEqual(date.value()));
  }

  public Logical isEqualOrAfter(Date date) {
    return Logical.create(value.isEqual(date.value()) || value.isAfter(value));
  }

  public Logical isEqualOrBefore(Date date) {
    return Logical.create(value.isEqual(date.value()) || value.isBefore(value));
  }

  public Logical isMonday() {
    return Logical.create(value.getDayOfWeek().getValue() == 1);
  }
}
