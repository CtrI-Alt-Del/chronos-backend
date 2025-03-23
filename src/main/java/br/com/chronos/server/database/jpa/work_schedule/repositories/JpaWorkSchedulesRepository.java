package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorWorkSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.server.database.jpa.work_schedule.mappers.DayOffMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WeekdayScheduleMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkScheduleMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WeekdayScheduleModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkScheduleModel;
import kotlin.Pair;

interface JpaWorkScheduleModelsRepository extends JpaRepository<WorkScheduleModel, UUID> {
  @Query(value = "SELECT EXISTS (SELECT 1 FROM collaborators WHERE work_schedule_id = :work_schedule_id)", nativeQuery = true)
  boolean hasAnyCollaborator(@Param("work_schedule_id") UUID workScheduleId);
}

interface JpaDayOffModelsRepository extends JpaRepository<DayOffModel, UUID> {

}

interface JpaWeekdayScheduleModelsRepository extends JpaRepository<WeekdayScheduleModel, UUID> {

}

public class JpaWorkSchedulesRepository implements WorkSchedulesRepository {
  @Autowired
  private JpaWorkScheduleModelsRepository workScheduleModelsRepository;

  @Autowired
  private JpaDayOffModelsRepository dayOffModelsRepository;

  @Autowired
  private JpaWeekdayScheduleModelsRepository weekdayScheduleModelsRepository;

  @Autowired
  JpaTimePunchModelsRepository timePunchModelsRepository;

  @Autowired
  private DayOffMapper dayOffMapper;

  @Autowired
  private WeekdayScheduleMapper weekdayScheduleMapper;

  @Autowired
  private WorkScheduleMapper mapper;

  @Override
  public Optional<WorkSchedule> findById(Id workScheduleId) {
    var workSchedule = workScheduleModelsRepository.findById(workScheduleId.value());
    if (workSchedule.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapper.toEntity(workSchedule.get()));
  }

  @Override
  public Array<WorkSchedule> findAll() {
    var workScheduleModels = workScheduleModelsRepository.findAll();
    return Array.createFrom(workScheduleModels, mapper::toEntity);
  }

  @Override
  public Pair<Array<WorkSchedule>, PlusInteger> findMany(Page page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var workScheduleModels = workScheduleModelsRepository.findAll(pageRequest);
    var items = workScheduleModels.stream().toList();
    var itemsCount = workScheduleModels.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, mapper::toEntity),
        PlusInteger.create((int) itemsCount, "contagem de escalas"));
  }

  @Override
  public Array<CollaboratorWorkSchedule> findAllCollaboratorWorkSchedules() {
    throw new UnsupportedOperationException("Unimplemented method 'findAllCollaboratorWorkSchedules'");
  }

  @Override
  @Transactional
  public void add(WorkSchedule workSchedule) {
    var workScheduleModel = mapper.toModel(workSchedule);
    workScheduleModelsRepository.save(workScheduleModel);
    addWeekSchedule(workSchedule);
    addDaysOffSchedule(workSchedule);
  }

  @Override
  @Transactional
  public void addMany(Array<WorkSchedule> workSchedules) {
    var workScheduleModels = workSchedules.map(mapper::toModel);
    workScheduleModelsRepository.saveAll(workScheduleModels.list());

    for (var workSchedule : workSchedules.list()) {
      addWeekSchedule(workSchedule);
      addDaysOffSchedule(workSchedule);
    }
  }

  private void addWeekSchedule(WorkSchedule workSchedule) {
    var weekdayScheduleModels = workSchedule.getWeekSchedule().map((weekdaySchedule) -> {
      var weekdayScheduleModel = weekdayScheduleMapper.toModel(weekdaySchedule);
      timePunchModelsRepository.save(weekdayScheduleModel.getTimePunch());
      weekdayScheduleModel.setWorkSchedule(mapper.toModel(workSchedule));
      return weekdayScheduleModel;
    });
    weekdayScheduleModelsRepository.saveAll(weekdayScheduleModels.list());
  }

  private void addDaysOffSchedule(WorkSchedule workSchedule) {
    var daysOffModel = workSchedule.getDaysOffSchedule().days().map((dayOff) -> {
      var dayOffModel = dayOffMapper.toModel(dayOff);
      dayOffModel.setWorkSchedule(mapper.toModel(workSchedule));
      return dayOffModel;
    });
    dayOffModelsRepository.saveAll(daysOffModel.list());
  }

  @Override
  public void update(WorkSchedule workSchedule) {
    var workScheduleModel = mapper.toModel(workSchedule);
    workScheduleModelsRepository.save(workScheduleModel);
  }

  @Override
  public void updateMany(Array<WorkSchedule> workSchedules) {
    var workScheduleModels = workSchedules.map(mapper::toModel);
    workScheduleModelsRepository.saveAll(workScheduleModels.list());
  }

  @Override
  public void remove(WorkSchedule workSchedule) {
    var workScheduleModel = mapper.toModel(workSchedule);
    workScheduleModelsRepository.delete(workScheduleModel);
  }

  @Override
  public Logical hasAnyCollaborator(Id workScheduleId) {
    return Logical.create(workScheduleModelsRepository.hasAnyCollaborator(workScheduleId.value()));
  }

}
