package br.com.chronos.core.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import kotlin.Pair;

public interface WorkdayLogsRepository {
  Optional<WorkdayLog> findByCollaboratorAndDate(Id collaboratorId, Date date);

  Pair<Array<WorkdayLog>, PlusIntegerNumber> findManyByCollaboratorAndDateRange(
      Id collaboratorId,
      DateRange dateRange,
      PageNumber page);

  Pair<Array<WorkdayLog>, PlusIntegerNumber> findManyByDateAndCollaborationSector(
      Date date,
      CollaborationSector sector,
      PageNumber page);

  void add(WorkdayLog workdayLogs);

  void addMany(Array<WorkdayLog> workdayLogs);

  void removeManyByDate(Date date);

  Logical hasTimePunchLog(TimePunch timePunch);
}
