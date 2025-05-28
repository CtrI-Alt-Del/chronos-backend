package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.records.PlusIntegerNumber;

public record MonthlyAbsence(PlusIntegerNumber collaboratorAbsence, PlusIntegerNumber managerAbsence) {
  public static MonthlyAbsence create(int collaboratorAbsence, int managerAbsence) {
    return new MonthlyAbsence(PlusIntegerNumber.create(collaboratorAbsence), PlusIntegerNumber.create(managerAbsence));
  }
}
