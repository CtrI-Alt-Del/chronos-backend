package br.com.chronos.core.modules.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.records.Page;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;

public interface WorkdayLogsRepository {
  Optional<WorkdayLog> findByCollaboratorAndDate(Id collaboratorId, Date date);

  Array<WorkdayLog> findManyByCollaboratorAndDateRange(
      Id collaboratorId,
      Date startDate,
      Date endDate,
      Page page);

  void addMany(Array<WorkdayLog> workdayLogs);
}
