package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import kotlin.Pair;

public interface WorkSchedulesRepository {
  Optional<WorkSchedule> findById(Id workScheduleId);

  Array<WorkSchedule> findAllWithAnyCollaborator();

  Array<WorkSchedule> findAll();

  Array<Id> findCollaboratorIdsByWorkSchedule(WorkSchedule workSchedule);

  Pair<Array<WorkSchedule>, PlusInteger> findMany(Page page);

  void add(WorkSchedule workSchedule);

  void addMany(Array<WorkSchedule> workSchedules);

  void update(WorkSchedule workSchedule);

  void updateMany(Array<WorkSchedule> workSchedules);

  void remove(WorkSchedule workScheduleId);

  Logical hasAnyCollaborator(Id workScheduleId);
}
