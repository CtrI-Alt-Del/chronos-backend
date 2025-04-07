package br.com.chronos.core.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.entities.WorkSchedule;
import kotlin.Pair;

public interface WorkSchedulesRepository {
  Optional<WorkSchedule> findById(Id workScheduleId);

  Array<WorkSchedule> findAllWithAnyCollaborator();

  Array<WorkSchedule> findAll();

  Array<Id> findCollaboratorIdsByWorkSchedule(WorkSchedule workSchedule);

  Pair<Array<WorkSchedule>, PlusIntegerNumber> findMany(PageNumber page);

  void add(WorkSchedule workSchedule);

  void addMany(Array<WorkSchedule> workSchedules);

  void update(WorkSchedule workSchedule);

  void updateWeekSchedule(WorkSchedule workSchedule);

  void updateDaysOffSchedule(WorkSchedule workSchedule);

  void updateMany(Array<WorkSchedule> workSchedules);

  void remove(WorkSchedule workSchedule);

  Logical hasAnyCollaborator(Id workScheduleId);

  Logical hasTimePunchSchedule(TimePunch timePunch);
}
