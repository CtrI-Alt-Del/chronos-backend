package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.chronos.core.work_schedule.domain.dtos.MonthlyAbsenceDto;
import br.com.chronos.core.work_schedule.domain.dtos.YearlyAbsenceReportDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.domain.records.MonthlyAbsence;

public class GetYearlyAbsenceReportUseCase {
  private final WorkdayLogsRepository repository;

  public GetYearlyAbsenceReportUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public YearlyAbsenceReportDto execute(LocalDate startDate, LocalDate endDate) {
    if (startDate == null || endDate == null) {
      endDate = LocalDate.now();
      startDate = endDate.minusMonths(11).withDayOfMonth(1);
    }
    var chartValues = repository.getYearlyCollaboratorsAbsence(startDate, endDate);

    List<MonthlyAbsenceDto> monthlyAbsenceDtos = new ArrayList<>();

    int currentMonth = startDate.getMonthValue();
    int currentYear = startDate.getYear();

    for (int i = 0; i < chartValues.list().size(); i++) {
      MonthlyAbsence absence = chartValues.list().get(i);

      MonthlyAbsenceDto dto = new MonthlyAbsenceDto()
          .setMonth(currentMonth)
          .setCollaboratorsAbsence(absence.collaboratorAbsence().value())
          .setManagersAbsence(absence.managerAbsence().value());

      monthlyAbsenceDtos.add(dto);

      if (currentMonth == 12) {
        currentMonth = 1;
        currentYear++;
      } else {
        currentMonth++;
      }
    }

    return new YearlyAbsenceReportDto()
        .setMonthlyAbsences(monthlyAbsenceDtos);
  }

}
