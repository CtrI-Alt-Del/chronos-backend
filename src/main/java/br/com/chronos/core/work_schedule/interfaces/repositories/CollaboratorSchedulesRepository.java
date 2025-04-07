package br.com.chronos.core.work_schedule.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.records.CollaboratorSchedule;

public interface CollaboratorSchedulesRepository {
  Array<CollaboratorSchedule> findAll();

  Optional<CollaboratorSchedule> findByCollaborator(Id collaborator);

  void add(CollaboratorSchedule collaboratorSchedule);

  void addMany(Array<CollaboratorSchedule> collaboratorSchedule);
}
