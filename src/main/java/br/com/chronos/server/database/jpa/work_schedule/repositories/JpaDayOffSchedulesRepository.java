package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.mappers.DayOffMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.DayOffScheduleMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffModel;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffScheduleModel;

interface JpaDayOffScheduleModelsRepository extends JpaRepository<DayOffScheduleModel, UUID> {
  Optional<DayOffScheduleModel> findByCollaborator(CollaboratorModel collaborator);
}

interface JpaDayOffModelsRepository extends JpaRepository<DayOffModel, UUID> {
  @Modifying
  @Query(value = "DELETE FROM days_off WHERE day_off_schedule_id = :dayOffScheduleId", nativeQuery = true)
  void deleteManyByDayOffSchedule(@Param("dayOffScheduleId") UUID dayOffScheduleId);
}

public class JpaDayOffSchedulesRepository implements DayOffSchedulesRepository {
  @Autowired
  private JpaDayOffScheduleModelsRepository dayOffScheduleModelsRepository;

  @Autowired
  private JpaDayOffModelsRepository dayOffModelsRepository;

  @Autowired
  private DayOffScheduleMapper dayOffScheduleMapper;

  @Autowired
  private DayOffMapper dayOffMapper;

  @Override
  public Optional<DayOffSchedule> findByCollaborator(Id collaborator) {
    var collaboratorModel = CollaboratorModel.builder().id(collaborator.value()).build();
    var dayOffScheduleModel = dayOffScheduleModelsRepository.findByCollaborator(collaboratorModel);
    if (dayOffScheduleModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(dayOffScheduleMapper.toEntity(dayOffScheduleModel.get()));
  }

  @Override
  @Transactional
  public void add(DayOffSchedule dayOffSchedule, Id collaboratorId) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var dayOffScheduleModel = dayOffScheduleMapper.toModel(dayOffSchedule);
    dayOffScheduleModel.setCollaborator(collaboratorModel);

    var dayOffModels = dayOffSchedule.getDaysOff().map((dayOff) -> {
      var dayOffModel = dayOffMapper.toModel(dayOff);
      dayOffModel.setDayOffSchedule(dayOffScheduleModel);
      return dayOffModel;
    });

    dayOffScheduleModelsRepository.save(dayOffScheduleModel);
    dayOffModelsRepository.saveAll(dayOffModels.list());
  }

  @Override
  @Transactional
  public void addMany(Array<DayOffSchedule> dayOffSchedules, Id collaboratorId) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    Array<DayOffModel> allDayOffModels = Array.createAsEmpty();

    var dayOffScheduleModels = dayOffSchedules.map((dayOffSchedule) -> {
      var dayOffScheduleModel = dayOffScheduleMapper.toModel(dayOffSchedule);
      dayOffScheduleModel.setCollaborator(collaboratorModel);

      var dayOffModels = dayOffSchedule.getDaysOff().map((dayOff) -> {
        var dayOffModel = dayOffMapper.toModel(dayOff);
        dayOffModel.setDayOffSchedule(dayOffScheduleModel);
        return dayOffModel;
      });

      allDayOffModels.addArray(dayOffModels);
      return dayOffScheduleModel;
    });
    dayOffScheduleModelsRepository.saveAll(dayOffScheduleModels.list());
    dayOffModelsRepository.saveAll(allDayOffModels.list());
  }

  @Override
  @Transactional
  public void replace(DayOffSchedule dayOffSchedule, Id collaboratorId) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var dayOffScheduleModel = dayOffScheduleModelsRepository.findByCollaborator(collaboratorModel);
    dayOffModelsRepository.deleteManyByDayOffSchedule(dayOffScheduleModel.get().getId());

    var dayOffModels = dayOffSchedule.getDaysOff().map((dayOff) -> {
      var dayOffModel = dayOffMapper.toModel(dayOff);
      dayOffModel.setDayOffSchedule(dayOffScheduleModel.get());
      return dayOffModel;
    });
    dayOffModelsRepository.saveAll(dayOffModels.list());
  }

  @Override
  public Array<DayOffSchedule> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public void replaceMany(Array<DayOffSchedule> dayOffSchedule) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'replaceMany'");
  }

}
