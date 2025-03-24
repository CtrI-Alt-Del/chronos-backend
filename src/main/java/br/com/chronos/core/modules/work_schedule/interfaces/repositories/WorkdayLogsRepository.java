package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.DateRange;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;
import kotlin.Pair;

public interface WorkdayLogsRepository {
  Optional<WorkdayLog> findByCollaboratorAndDate(Id collaboratorId, Date date);

  Pair<Array<WorkdayLog>, PlusInteger> findManyByCollaboratorAndDateRange(
      Id collaboratorId,
      DateRange dateRange,
      Page page);

  Pair<Array<WorkdayLog>, PlusInteger> findManyByDate(Date date, Page page);

  void addMany(Array<WorkdayLog> workdayLogs);

  void removeManyByDate(Date date);

  Logical hasTimePunchLog(TimePunch timePunch);
}
