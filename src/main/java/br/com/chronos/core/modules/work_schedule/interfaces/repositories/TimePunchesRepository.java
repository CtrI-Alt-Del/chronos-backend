package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;

public interface TimePunchesRepository {
  Optional<TimePunch> findById(Id timePunchId);

  void update(TimePunch timePunch);
}
