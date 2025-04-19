package br.com.chronos.core.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import kotlin.Pair;

public interface WorkdayLogsRepository {
  Array<WorkdayLog> findAllByDate(Date date);

  Optional<WorkdayLog> findByCollaboratorAndDate(Id collaboratorId, Date date);

  Array<WorkdayLog> findAllByCollaboratorAndDateRange(Id collaboratorId, DateRange dateRange);

  Optional<WorkdayLog> findByTimePunch(Id timePunchId);

  Pair<Array<WorkdayLog>, PlusIntegerNumber> findManyByCollaboratorAndDateRange(
      Id collaboratorId,
      DateRange dateRange,
      PageNumber page);

  Pair<Array<WorkdayLog>, PlusIntegerNumber> findManyByDateAndCollaborationSector(
      Date date,
      Text collaboratorName,
      CollaborationSector sector,
      PageNumber page);

  void add(WorkdayLog workdayLogs);

  void addMany(Array<WorkdayLog> workdayLogs);

  void replaceMany(Array<WorkdayLog> workdayLogs);

  void removeManyByDate(Date date);

  Logical hasTimePunch(TimePunch timePunch);
}
