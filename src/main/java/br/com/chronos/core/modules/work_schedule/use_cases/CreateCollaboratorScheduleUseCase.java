package br.com.chronos.core.modules.work_schedule.use_cases;

import java.util.List;

import br.com.chronos.core.modules.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;

public class CreateCollaboratorScheduleUseCase {
  private final WeekdaySchedulesRepository weekdaySchedulesRepository;
  private final DayOffSchedulesRepository dayOffSchedulesRepository;

  public CreateCollaboratorScheduleUseCase(
      WeekdaySchedulesRepository weekdaySchedulesRepository,
      DayOffSchedulesRepository dayOffSchedulesRepository) {
    this.weekdaySchedulesRepository = weekdaySchedulesRepository;
    this.dayOffSchedulesRepository = dayOffSchedulesRepository;
  }

  public void execute(
      String collaboratorId,
      List<WeekdayScheduleDto> weekdaySchedulesDto,
      DayOffScheduleDto dayOffScheduleDto) {
    var collaboratorSchedule = CollaboratorSchedule.create(
        collaboratorId,
        weekdaySchedulesDto,
        dayOffScheduleDto);

    weekdaySchedulesRepository.addMany(
        collaboratorSchedule.weekSchedule(),
        collaboratorSchedule.collaboratorId());
    dayOffSchedulesRepository.add(
        collaboratorSchedule.daysOffSchedule(),
        collaboratorSchedule.collaboratorId());
  }
}
