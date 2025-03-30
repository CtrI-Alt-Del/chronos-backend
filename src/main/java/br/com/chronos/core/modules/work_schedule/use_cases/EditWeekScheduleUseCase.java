package br.com.chronos.core.modules.work_schedule.use_cases;

import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WeekdaySchedule;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.WorkScheduleNotFoundException;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class EditWeekScheduleUseCase {
  private final WorkSchedulesRepository repository;

  public EditWeekScheduleUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute(String workScheduleId, List<WeekdayScheduleDto> weekScheduleDto) {
    var workSchedule = findWorkSchedule(Id.create(workScheduleId));
    workSchedule.updateWeekSchedule(Array.createFrom(weekScheduleDto, WeekdaySchedule::new));
    repository.updateWeekSchedule(workSchedule);
  }

  private WorkSchedule findWorkSchedule(Id id) {
    var workSchedule = repository.findById(id);
    if (workSchedule.isEmpty()) {
      throw new WorkScheduleNotFoundException();
    }
    return workSchedule.get();
  }
}
