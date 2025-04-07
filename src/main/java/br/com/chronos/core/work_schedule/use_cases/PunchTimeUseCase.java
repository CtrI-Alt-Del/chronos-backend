package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.exceptions.TimePunchNotFoundException;
import br.com.chronos.core.work_schedule.interfaces.repositories.TimePunchesRepository;

public class PunchTimeUseCase {
  private final TimePunchesRepository repository;

  public PunchTimeUseCase(TimePunchesRepository repository) {
    this.repository = repository;
  }

  public TimePunchDto execute(String timePunchId, LocalTime time) {
    var timePunch = findTimePunch(timePunchId);
    timePunch.punch(Time.create(time));
    repository.update(timePunch);
    return timePunch.getDto();
  }

  private TimePunch findTimePunch(String timePunchId) {
    var timePunch = repository.findById(Id.create(timePunchId));
    if (timePunch.isEmpty()) {
      throw new TimePunchNotFoundException();
    }

    return timePunch.get();
  }
}
