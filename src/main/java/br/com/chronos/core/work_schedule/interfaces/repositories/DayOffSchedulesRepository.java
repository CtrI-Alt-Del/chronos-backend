package br.com.chronos.core.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;

public interface DayOffSchedulesRepository {
  Array<DayOffSchedule> findAll();

  Optional<DayOffSchedule> findByCollaborator(Id collaborator);

  void add(DayOffSchedule dayOffSchedule, Id collaborator);

  void addMany(Array<DayOffSchedule> dayOffSchedules, Id collaborator);

  void replace(DayOffSchedule dayOffSchedule, Id collaborator);

  void replaceMany(Array<DayOffSchedule> dayOffSchedule);

}
