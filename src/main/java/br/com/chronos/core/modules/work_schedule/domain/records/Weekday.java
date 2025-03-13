package br.com.chronos.core.modules.work_schedule.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

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
      throw new ValidationException("Weekday", "must be a valid name for weekday");
    }
  }

  public String toString() {
    return name.toString();
  }
}
