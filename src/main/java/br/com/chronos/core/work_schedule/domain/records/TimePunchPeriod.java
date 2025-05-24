package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Logical;

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
    return name.toString().toLowerCase();
  }

  public Logical isClockIn() {
    return name == PeriodName.FIRST_CLOCK_IN || name == PeriodName.SECOND_CLOCK_IN ? Logical.createAsTrue()
        : Logical.createAsFalse();
  }

  public Logical isClockOut() {
    return name == PeriodName.FIRST_CLOCK_OUT || name == PeriodName.SECOND_CLOCK_OUT ? Logical.createAsTrue()
        : Logical.createAsFalse();
  }
}
