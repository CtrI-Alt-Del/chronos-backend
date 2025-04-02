package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.CollaboratorSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

interface JpaCollaboratorModelRepository extends JpaRepository<CollaboratorModel, UUID> {
  @Query(value = "SELECT id FROM collaborators", nativeQuery = true)
  List<UUID> findAllCollaboratorIds();
}

public class JpaCollaboratorSchedulesRepository implements CollaboratorSchedulesRepository {
  @Autowired
  private JpaCollaboratorModelRepository collaboratorModelsRepository;

  private WeekdaySchedulesRepository weekdaySchedulesRepository;

  private DayOffSchedulesRepository dayOffSchedulesRepository;

  public JpaCollaboratorSchedulesRepository(WeekdaySchedulesRepository weekdaySchedulesRepository,
      DayOffSchedulesRepository dayOffSchedulesRepository) {
    this.weekdaySchedulesRepository = weekdaySchedulesRepository;
    this.dayOffSchedulesRepository = dayOffSchedulesRepository;
  }

  @Override
  public Array<CollaboratorSchedule> findAll() {
    Array<CollaboratorSchedule> collaboratorSchedules = Array.createAsEmpty();

    var collaboratorIds = collaboratorModelsRepository.findAllCollaboratorIds();
    for (var collaboratorId : collaboratorIds) {
      var id = Id.create(collaboratorId.toString());
      var weekSchedule = weekdaySchedulesRepository.findManyByCollaborator(id);
      var dayOffSchedule = dayOffSchedulesRepository.findByCollaborator(id);

      var collaboratorSchedule = CollaboratorSchedule.create(id, weekSchedule, dayOffSchedule.get());
      collaboratorSchedules.add(collaboratorSchedule);
    }

    return collaboratorSchedules;
  }

  @Override
  @Transactional
  public void add(CollaboratorSchedule collaboratorSchedule) {
    weekdaySchedulesRepository.addMany(
        collaboratorSchedule.weekSchedule(),
        collaboratorSchedule.collaboratorId());
    dayOffSchedulesRepository.add(
        collaboratorSchedule.daysOffSchedule(),
        collaboratorSchedule.collaboratorId());
  }

  @Override
  public void addMany(Array<CollaboratorSchedule> collaboratorSchedules) {
    for (var collaboratorSchedule : collaboratorSchedules.list()) {
      add(collaboratorSchedule);
    }
  }

  @Override
  public Optional<CollaboratorSchedule> findByCollaborator(Id collaborator) {
    var weekSchedule = weekdaySchedulesRepository.findManyByCollaborator(collaborator);
    var dayOffSchedule = dayOffSchedulesRepository.findByCollaborator(collaborator);

    if (dayOffSchedule.isEmpty()) {
      return Optional.empty();
    }

    var collaboratorSchedules = CollaboratorSchedule.create(collaborator, weekSchedule, dayOffSchedule.get());
    return Optional.of(collaboratorSchedules);
  }

}
