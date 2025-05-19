package br.com.chronos.core.work_schedule.domain.records;


public record MonthlyAbsence(int collaboratorAbsence, int managerAbsence) {
  public static MonthlyAbsence create(int collaboratorAbsence, int managerAbsence) {
    return new MonthlyAbsence(collaboratorAbsence, managerAbsence);
  }
}
