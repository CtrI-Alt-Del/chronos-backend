package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
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
  @Query(value = "DELETE FROM days_off WHERE work_schedule_id = :collaboratorId", nativeQuery = true)
  void deleteManyByCollaborator(@Param("collaboratorId") UUID collaboratorId);
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
  public void add(DayOffSchedule dayOffSchedule, Id collaborator) {
    var dayOffScheduleModel = dayOffScheduleMapper.toModel(dayOffSchedule);
    dayOffScheduleModelsRepository.save(dayOffScheduleModel);
    var daysOffModels = toDayOffModels(dayOffSchedule);
    dayOffModelsRepository.saveAll(daysOffModels.list());

  }

  @Override
  @Transactional
  public void addMany(Array<DayOffSchedule> dayOffSchedules, Id collaborator) {
    Array<DayOffModel> dayOffModels = Array.createAsEmpty();
    var dayOffScheduleModels = dayOffSchedules.map((dayOffSchedule) -> {
      dayOffModels.addArray(toDayOffModels(dayOffSchedule));
      return dayOffScheduleMapper.toModel(dayOffSchedule);
    });
    dayOffScheduleModelsRepository.saveAll(dayOffScheduleModels.list());
    dayOffModelsRepository.saveAll(dayOffModels.list());
  }

  private Array<DayOffModel> toDayOffModels(DayOffSchedule dayOffSchedule) {
    return dayOffSchedule.getDaysOff().map((dayOff) -> {
      var dayOffModel = dayOffMapper.toModel(dayOff);
      dayOffModel.setDayOffSchedule(dayOffScheduleMapper.toModel(dayOffSchedule));
      return dayOffModel;
    });
  }

  @Override
  public void replace(DayOffSchedule dayOffSchedule, Id collaborator) {
    throw new UnsupportedOperationException("Unimplemented method 'replace'");
  }

}
