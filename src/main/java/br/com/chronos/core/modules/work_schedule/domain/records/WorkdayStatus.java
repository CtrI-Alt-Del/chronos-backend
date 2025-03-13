package br.com.chronos.core.modules.work_schedule.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public record WorkdayStatus(WorkdayStatusName name) {
  public enum WorkdayStatusName {
    NORMAL,
    ABSENCE,
    DAY_OFF,
    HOLIDAY,
    WORK_LEAVE
  }

  public static WorkdayStatus create(String value) {
    if (value == null) {
      return new WorkdayStatus(WorkdayStatusName.NORMAL);
    }

    try {
      var workdayStatusName = WorkdayStatusName.valueOf(value.toUpperCase());
      return new WorkdayStatus(workdayStatusName);
    } catch (Exception e) {
      throw new ValidationException(value, "must be a valid name for work day status");
    }
  }

  public String toString() {
    return name.toString();
  }
}
