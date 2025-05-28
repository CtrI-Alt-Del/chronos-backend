package br.com.chronos.core.work_schedule.domain.dtos;

public class MonthlyAbsenceDto {
  public int collaboratorsAbsence;
  public int managersAbsence;
  public int month;

  public MonthlyAbsenceDto setCollaboratorsAbsence(int value) {
    this.collaboratorsAbsence = value;
    return this;
  }

  public MonthlyAbsenceDto setManagersAbsence(int value) {
    this.managersAbsence = value;
    return this;
  }

  public MonthlyAbsenceDto setMonth(int month) {
    this.month = month;
    return this;
  }

  public int getMonth() {
    return this.month;
  }

}
