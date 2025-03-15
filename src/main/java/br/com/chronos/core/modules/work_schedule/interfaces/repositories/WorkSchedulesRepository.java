package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;

public interface WorkSchedulesRepository {
  void add(WorkSchedule workSchedule);

  void update(WorkSchedule workSchedule);
}
