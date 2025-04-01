package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.CollaboratorSchedulesRepository;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

interface JpaCollaboratorModelRepository extends JpaRepository<CollaboratorModel, UUID> {
  @Query(value = "SELECT id FROM collaborators", nativeQuery = true)
  List<UUID> findAllCollaboratorIds();
}

public class JpaCollaboratorSchedulesRepository implements CollaboratorSchedulesRepository {
  private final JpaWeekdaySchedulesRepository weekdaySchedulesRespository = new JpaWeekdaySchedulesRepository();
  private final JpaDayOffSchedulesRepository dayOffSchedulesRespository = new JpaDayOffSchedulesRepository();

  @Autowired
  private JpaCollaboratorModelRepository collaboratorModelsRepository;

  @Override
  public Array<CollaboratorSchedule> findAll() {
    Array<CollaboratorSchedule> collaboratorSchedules = Array.createAsEmpty();

    var collaboratorIds = collaboratorModelsRepository.findAllCollaboratorIds();
    for (var collaboratorId : collaboratorIds) {
      var id = Id.create(collaboratorId.toString());
      var weekSchedule = weekdaySchedulesRespository.findManyByCollaborator(id);
      var dayOffSchedule = dayOffSchedulesRespository.findByCollaborator(id);

      var collaboratorSchedule = CollaboratorSchedule.create(id, weekSchedule, dayOffSchedule.get());
      collaboratorSchedules.add(collaboratorSchedule);
    }

    return collaboratorSchedules;
  }

  @Override
  @Transactional
  public void add(CollaboratorSchedule collaboratorSchedule) {
    weekdaySchedulesRespository.addMany(
        collaboratorSchedule.weekSchedule(),
        collaboratorSchedule.collaboratorId());
    dayOffSchedulesRespository.add(
        collaboratorSchedule.daysOffSchedule(),
        collaboratorSchedule.collaboratorId());
  }

}
