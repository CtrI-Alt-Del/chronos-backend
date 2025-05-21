package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Logical;

public record WorkdayStatus(WorkdayStatusName name) {
  public enum WorkdayStatusName {
    NORMAL_DAY,
    ABSENCE,
    DAY_OFF,
    HOLIDAY,
    WORK_LEAVE,
    EXCUSED_ABSENCE,
    VACATION
  }

  public static WorkdayStatus create(String value) {
    if (value == null) {
      return new WorkdayStatus(WorkdayStatusName.NORMAL_DAY);
    }

    try {
      var workdayStatusName = WorkdayStatusName.valueOf(value.toUpperCase());
      return new WorkdayStatus(workdayStatusName);
    } catch (Exception e) {
      throw new ValidationException(value, "must be a valid name for work day status");
    }
  }

  public static WorkdayStatus createAsNormalDay() {
    return new WorkdayStatus(WorkdayStatusName.NORMAL_DAY);
  }
  public static WorkdayStatus createAsExcusedAbsence() {
    return new WorkdayStatus(WorkdayStatusName.EXCUSED_ABSENCE);
  }

  public static WorkdayStatus createAsDayOff() {
    return new WorkdayStatus(WorkdayStatusName.DAY_OFF);
  }

  public static WorkdayStatus createAsAbsence() {
    return new WorkdayStatus(WorkdayStatusName.ABSENCE);
  }

  public static WorkdayStatus createAsHoliday() {
    return new WorkdayStatus(WorkdayStatusName.HOLIDAY);
  }

  public static WorkdayStatus createAsWorkLeave() {
    return new WorkdayStatus(WorkdayStatusName.WORK_LEAVE);
  }

  public static WorkdayStatus createAsVacation() {
    return new WorkdayStatus(WorkdayStatusName.VACATION);
  }

  public Logical isNormalDay() {
    return Logical.create(name == WorkdayStatusName.NORMAL_DAY);
  }
  public Logical isExcusedAbsence() {
    return Logical.create(name == WorkdayStatusName.EXCUSED_ABSENCE);
  }

  public Logical isAbsence() {
    return Logical.create(name == WorkdayStatusName.ABSENCE);
  }

  public Logical isDayOff() {
    return Logical.create(name == WorkdayStatusName.DAY_OFF);
  }

  public Logical isHoliday() {
    return Logical.create(name == WorkdayStatusName.HOLIDAY);
  }

  public Logical isWorkLeave() {
    return Logical.create(name == WorkdayStatusName.WORK_LEAVE);
  }

  public Logical isVacation() {
    return Logical.create(name == WorkdayStatusName.VACATION);
  }

  public String toString() {
    return name.toString().toLowerCase();
  }
}
