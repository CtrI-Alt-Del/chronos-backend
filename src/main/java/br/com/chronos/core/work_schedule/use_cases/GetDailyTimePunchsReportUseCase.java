package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.work_schedule.domain.dtos.DailyTimePunchsReportDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetDailyTimePunchsReportUseCase {
  private final WorkdayLogsRepository repository;

  public GetDailyTimePunchsReportUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public DailyTimePunchsReportDto execute(LocalDate date) {
    var chartValues = repository
        .getAllTimePunchsByDate(Date.create(date));
    return DailyTimePunchsReportDto.createFromValues(chartValues.list());
  }

}
