package br.com.chronos.core.work_schedule.domain.dtos;

import java.util.List;

import br.com.chronos.core.work_schedule.domain.records.MonthlyAbsence;

public class YearlyAbsenceReportDto {
  public List<MonthlyAbsenceDto> monthlyAbsences;

  public YearlyAbsenceReportDto setMonthlyAbsences(List<MonthlyAbsenceDto> monthlyAbsences) {
    this.monthlyAbsences = monthlyAbsences;
    return this;
  }

  public static YearlyAbsenceReportDto createFromValues(List<MonthlyAbsence> monthlyAbsences) {
    var monthlyAbsencesDto = monthlyAbsences.stream()
        .map(monthlyAbsence -> new MonthlyAbsenceDto()
            .setCollaboratorsAbsence(monthlyAbsence.collaboratorAbsence().value())
            .setManagersAbsence(monthlyAbsence.managerAbsence().value()))
        .toList();
    return new YearlyAbsenceReportDto()
        .setMonthlyAbsences(monthlyAbsencesDto);
  }
}
