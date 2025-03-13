package br.com.chronos.core.modules.global.domain.records;

import java.time.LocalDate;
import java.time.ZoneId;

public record Date(LocalDate value, ZoneId zoneArea) {
  public static Date create(LocalDate value, String zoneArea) {
    return new Date(value, ZoneId.of(zoneArea));
  }

  public static Date create(LocalDate value) {
    return new Date(value, ZoneId.of("America/Sao_Paulo"));
  }

  public Logical isEqualOrAfter(Date date) {
    return Logical.create(value.isEqual(date.value()) || value.isAfter(value));
  }

  public Logical isEqualOrBefore(Date date) {
    return Logical.create(value.isEqual(date.value()) || value.isBefore(value));
  }
}
