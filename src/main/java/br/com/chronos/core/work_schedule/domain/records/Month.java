package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record Month(MonthName name) {
  public enum MonthName {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER
  }

  static public Month create(String value) {
    if (value == null) {
      return new Month(MonthName.JANUARY);
    }

    try {
      var monthName = MonthName.valueOf(value.toUpperCase());
      return new Month(monthName);
    } catch (Exception e) {
      throw new ValidationException(value, "dever ser um nome válido de mês");
    }
  }
}