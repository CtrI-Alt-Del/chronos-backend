package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class JpaWorkdayLogsRepository implements WorkdayLogsRepository {

  @Override
  public Optional<WorkdayLog> findByCollaboratorAndDate(Id collaboratorId, Date date) {
    throw new UnsupportedOperationException("Unimplemented method 'findByCollaboratorAndDate'");
  }

  @Override
  public void addMany(Array<WorkdayLog> workdayLogs) {
    throw new UnsupportedOperationException("Unimplemented method 'addMany'");
  }

}
