package br.com.chronos.core.work_schedule.use_cases;

import java.util.List;

import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.dtos.TimeCardRowDto;
import br.com.chronos.core.work_schedule.domain.records.TimeCard;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetTimeCardUseCase {
  private final WorkdayLogsRepository repository;

  public GetTimeCardUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public List<TimeCardRowDto> execute(String collaboratorId, int month, int year) {
    var dateRange = DateRange.createAsMonthRange(month, year);
    var workdayLogs = repository.findAllByCollaboratorAndDateRange(
        Id.create(collaboratorId),
        dateRange);
    var timeCard = TimeCard.create(workdayLogs);
    return timeCard.rows().map((row) -> row.getDto()).list();
  }
}
