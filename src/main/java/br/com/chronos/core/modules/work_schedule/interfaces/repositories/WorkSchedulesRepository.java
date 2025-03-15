package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;

public interface WorkSchedulesRepository {
  Optional<WorkSchedule> findByIdAndWithoutCollaborator(Id workScheduleId);

  void add(WorkSchedule workSchedule);

  void update(WorkSchedule workSchedule);

  void remove(WorkSchedule workScheduleId);
}
