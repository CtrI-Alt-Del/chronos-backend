package br.com.chronos.core.modules.work_schedule.use_cases;

import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;

public class UpdateWeekScheduleUseCase {
  private final WeekdaySchedulesRepository repository;

  public UpdateWeekScheduleUseCase(WeekdaySchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, List<WeekdayScheduleDto> weekScheduleDto) {
    var weekdaySchedules = CollaboratorSchedule.createWeekSchedule(weekScheduleDto);
    repository.replaceMany(weekdaySchedules, Id.create(collaboratorId));
  }
}
