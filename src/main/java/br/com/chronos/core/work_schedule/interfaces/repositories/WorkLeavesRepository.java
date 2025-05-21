package br.com.chronos.core.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.records.WorkLeave;

public interface WorkLeavesRepository {
  void add(WorkLeave workLeave, Id collaboratorId);

  Optional<WorkLeave> findByCollaboratorAndDate(Id collaboratorId, Date date);
}
