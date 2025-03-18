package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorWorkSchedule;

public interface WorkSchedulesRepository {
  Optional<WorkSchedule> findByIdAndWithoutCollaborator(Id workScheduleId);

  Array<CollaboratorWorkSchedule> findAllCollaboratorWorkSchedules();

  Array<WorkSchedule> findAll();

  void add(WorkSchedule workSchedule);

  void update(WorkSchedule workSchedule);

  void remove(WorkSchedule workScheduleId);
}
