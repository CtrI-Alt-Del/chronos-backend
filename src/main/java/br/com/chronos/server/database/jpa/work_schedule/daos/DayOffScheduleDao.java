package br.com.chronos.server.database.jpa.work_schedule.daos;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffScheduleModel;

public interface DayOffScheduleDao extends JpaRepository<DayOffScheduleModel, UUID> {
  Optional<DayOffScheduleModel> findByCollaborator(CollaboratorModel collaborator);
}
