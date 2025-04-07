package br.com.chronos.core.work_schedule.interfaces.repositories;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.entities.WeekdaySchedule;

public interface WeekdaySchedulesRepository {
  Array<WeekdaySchedule> findManyByCollaborator(Id collaborator);

  void addMany(Array<WeekdaySchedule> weekdaysSchedule, Id collaborator);

  void replaceMany(Array<WeekdaySchedule> weekdaysSchedule, Id collaborator);
}
