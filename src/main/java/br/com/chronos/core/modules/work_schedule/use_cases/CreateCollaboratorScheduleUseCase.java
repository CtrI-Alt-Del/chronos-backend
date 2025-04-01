package br.com.chronos.core.modules.work_schedule.use_cases;

import java.util.List;

import br.com.chronos.core.modules.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.CollaboratorSchedulesRepository;

public class CreateCollaboratorScheduleUseCase {
  private final CollaboratorSchedulesRepository repository;

  public CreateCollaboratorScheduleUseCase(CollaboratorSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute(
      String collaboratorId,
      List<WeekdayScheduleDto> weekdaySchedulesDto,
      DayOffScheduleDto dayOffScheduleDto) {
    var collaboratorSchedule = CollaboratorSchedule.create(
        collaboratorId,
        weekdaySchedulesDto,
        dayOffScheduleDto);

    repository.add(collaboratorSchedule);
  }
}
