package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.TimePunchNotFoundException;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;

public class EditTimePunchScheduleUseCase {
  private final TimePunchesRepository repository;

  public EditTimePunchScheduleUseCase(TimePunchesRepository repository) {
    this.repository = repository;
  }

  public TimePunchDto execute(TimePunchDto timePunchDto) {
    var newTimePunch = new TimePunch(timePunchDto);
    var oldTimePunch = findOldTimePunch(newTimePunch.getId());
    oldTimePunch.replaceWith(newTimePunch);
    repository.update(oldTimePunch);
    return oldTimePunch.getDto();
  }

  private TimePunch findOldTimePunch(Id id) {
    var timePunch = repository.findById(id);
    if (timePunch.isEmpty()) {
      throw new TimePunchNotFoundException();
    }
    return timePunch.get();
  }
}
