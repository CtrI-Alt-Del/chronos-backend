package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorWorkSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import kotlin.Pair;

public class JpaWorkSchedulesRepository implements WorkSchedulesRepository {
  @Override
  public Optional<WorkSchedule> findById(Id workScheduleId) {
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public Array<WorkSchedule> findAll() {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Pair<Array<WorkSchedule>, PlusInteger> findMany(Page page) {
    throw new UnsupportedOperationException("Unimplemented method 'findMany'");
  }

  @Override
  public Array<CollaboratorWorkSchedule> findAllCollaboratorWorkSchedules() {
    throw new UnsupportedOperationException("Unimplemented method 'findAllCollaboratorWorkSchedules'");
  }

  @Override
  public void add(WorkSchedule workSchedule) {
    throw new UnsupportedOperationException("Unimplemented method 'add'");
  }

  @Override
  public void update(WorkSchedule workSchedule) {
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void updateMany(Array<WorkSchedule> workSchedules) {
    throw new UnsupportedOperationException("Unimplemented method 'updateMany'");
  }

  @Override
  public void remove(WorkSchedule workScheduleId) {
    throw new UnsupportedOperationException("Unimplemented method 'remove'");
  }

  @Override
  public Logical hasAnyCollaborator(Id workScheduleId) {
    throw new UnsupportedOperationException("Unimplemented method 'hasAnyCollaborator'");
  }

}
