package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.DayOffSchedule;

public interface DayOffSchedulesRepository {
  Optional<DayOffSchedule> findByCollaborator(Id collaborator);

  void add(DayOffSchedule dayOffSchedule, Id collaborator);

  void addMany(Array<DayOffSchedule> dayOffSchedules, Id collaborator);

  void replace(DayOffSchedule dayOffSchedule, Id collaborator);
}
