package br.com.chronos.core.global.domain.records;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.work_schedule.domain.records.Weekday;

public record Date(LocalDate value) {
  public static Date create(LocalDate value, String zoneArea) {
    return new Date(value);
  }

  public static Date create(LocalDate value) {
    return new Date(value);
  }

  public static Date create(int year, int month, int day) {
    return new Date(LocalDate.of(year, month, day));
  }

  public static Date createFromNow() {
    return new Date(LocalDate.now());
  }

  public DateTime toDatetime() {
    var localTime = LocalTime.now();
    return DateTime.create(value.atTime(localTime));
  }

  public Date plusDays(int daysCount) {
    return new Date(value.plusDays(daysCount));
  }

  public Date minusDays(int daysCount) {
    return new Date(value.minusDays(daysCount));
  }

  public Logical isBefore(Date date) {
    return Logical.create(value.isBefore(date.value()));
  }

  public Logical isAfter(Date date) {
    return Logical.create(value.isAfter(date.value()));
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

  public Weekday getWeekday() {
    return Weekday.create(value.getDayOfWeek().toString());
  }
}
