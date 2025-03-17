package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class JpaWorkSchedulesRepository implements WorkSchedulesRepository {

  @Override
  public void add(WorkSchedule workSchedule) {
    throw new UnsupportedOperationException("Unimplemented method 'add'");
  }

  @Override
  public void update(WorkSchedule workSchedule) {
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public Optional<WorkSchedule> findByIdAndWithoutCollaborator(Id workScheduleId) {
    throw new UnsupportedOperationException("Unimplemented method 'findByIdAndWithoutCollaborator'");
  }

  @Override
  public void remove(WorkSchedule workScheduleId) {
    throw new UnsupportedOperationException("Unimplemented method 'remove'");
  }

}
