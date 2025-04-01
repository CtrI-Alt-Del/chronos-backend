package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorSchedule;

public interface CollaboratorSchedulesRepository {
  Array<CollaboratorSchedule> findAll();

  void add(CollaboratorSchedule collaboratorSchedule);
}
