package br.com.chronos.core.modules.work_schedule.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public record TimePunchPeriod(PeriodName name) {
  public enum PeriodName {
    FIRST_CLOCK_IN,
    FIRST_CLOCK_OUT,
    SECOND_CLOCK_IN,
    SECOND_CLOCK_OUT,
  }

  public static TimePunchPeriod create(String value) {
    try {
      var timePunchPeriodName = PeriodName.valueOf(value.toUpperCase());
      return new TimePunchPeriod(timePunchPeriodName);
    } catch (Exception e) {
      throw new ValidationException(value, "must be a valid name for time punch period");
    }
  }

  public String toString() {
    return name.toString();
  }
}
