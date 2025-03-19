package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.DateRange;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;

public interface WorkdayLogsRepository {
  Optional<WorkdayLog> findByCollaboratorAndDate(Id collaboratorId, Date date);

  Array<WorkdayLog> findManyByCollaboratorAndDateRange(Id collaboratorId, DateRange dateRange, Page page);

  Array<WorkdayLog> findManyByDate(Date date, Page page);

  void addMany(Array<WorkdayLog> workdayLogs);
}
