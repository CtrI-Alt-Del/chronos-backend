package br.com.chronos.core.modules.work_schedule.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Time;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.TimePunchNotFoundException;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;

public class PunchUseCase {
  private final TimePunchesRepository repository;

  public PunchUseCase(TimePunchesRepository repository) {
    this.repository = repository;
  }

  public void execute(String timePunchId, LocalTime punch) {
    var timePunch = findTimePunch(timePunchId);
    timePunch.punch(Time.create(punch));
    repository.update(timePunch);
  }

  private TimePunch findTimePunch(String timePunchId) {
    var timePunch = repository.findById(Id.create(timePunchId));
    if (timePunch.isEmpty()) {
      throw new TimePunchNotFoundException();
    }

    return timePunch.get();
  }
}
