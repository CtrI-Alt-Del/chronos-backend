package br.com.chronos.core.work_schedule.domain.dtos;

import java.util.List;

import br.com.chronos.core.work_schedule.domain.records.MonthlyAbsence;

public class YearlyAbsenceChartDto {
  public List<MonthlyAbsence> monthlyAbsences;

  public YearlyAbsenceChartDto setMonthlyAbsences(List<MonthlyAbsence> monthlyAbsences) {
    this.monthlyAbsences = monthlyAbsences;
    return this;
  }

  public static YearlyAbsenceChartDto createFromValues(List<MonthlyAbsence> monthlyAbsences) {
    return new YearlyAbsenceChartDto()
        .setMonthlyAbsences(monthlyAbsences);
  }
}
