package br.com.chronos.core.modules.work_schedule.use_cases;

import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;

public class GetWeekScheduleUseCase {
  private final WeekdaySchedulesRepository repository;

  public GetWeekScheduleUseCase(WeekdaySchedulesRepository repository) {
    this.repository = repository;
  }

  public List<WeekdayScheduleDto> execute(String collaboratorId) {
    var weekdaySchedules = repository.findManyByCollaborator(Id.create(collaboratorId));
    return weekdaySchedules.map((weekdaySchedule) -> weekdaySchedule.getDto()).list();
  }

}
