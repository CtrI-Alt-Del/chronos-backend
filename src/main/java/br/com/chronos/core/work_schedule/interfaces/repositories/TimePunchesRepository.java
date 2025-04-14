package br.com.chronos.core.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;

public interface TimePunchesRepository {
  Optional<TimePunch> findById(Id timePunchId);

  void replace(TimePunch timePunch);

  void replaceMany(Array<TimePunch> timePunches);
}
