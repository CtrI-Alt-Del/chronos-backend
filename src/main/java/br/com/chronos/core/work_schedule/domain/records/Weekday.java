package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Logical;

public record Weekday(WeekdayName name) {
  public enum WeekdayName {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
  }

  public static Weekday create(String value) {
    try {
      var weekdayName = WeekdayName.valueOf(value.toUpperCase());
      return new Weekday(weekdayName);
    } catch (Exception e) {
      throw new ValidationException("Dia de semana", "Deve ser um dia da semana valido");
    }
  }

  public Logical isEqual(Weekday weekday) {
    return Logical.create(name.equals(weekday.name));
  }

  public String toString() {
    return name.toString();
  }
}
