package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kotlin.Pair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.PageNumber;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.server.database.jpa.work_schedule.mappers.DayOffMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.TimePunchMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WeekdayScheduleMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkScheduleMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WeekdayScheduleModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkScheduleModel;

interface JpaWorkScheduleModelsRepository extends JpaRepository<WorkScheduleModel, UUID> {
  @Query(value = "SELECT EXISTS (SELECT 1 FROM collaborators WHERE work_schedule_id = :workScheduleId)", nativeQuery = true)
  boolean hasAnyCollaborator(@Param("workScheduleId") UUID workScheduleId);

  @Query(value = "SELECT DISTINCT(work_schedule_id) FROM collaborators", nativeQuery = true)
  List<UUID> findWorkScheduleWithAnyCollaboratorIds();

  @Query(value = "SELECT id FROM collaborators WHERE work_schedule_id = :workScheduleId", nativeQuery = true)
  List<UUID> findCollaboratorIdsByWorkSchedule(@Param("workScheduleId") UUID workScheduleId);

  @Modifying
  @Query(value = "DELETE FROM work_schedules WHERE id = :workScheduleId", nativeQuery = true)
  void deleteById(@Param("workScheduleId") UUID workScheduleId);
}

interface JpaDayOffModelsRepository extends JpaRepository<DayOffModel, UUID> {
  @Modifying
  @Query(value = "DELETE FROM days_off WHERE work_schedule_id = :workScheduleId", nativeQuery = true)
  void deleteManyByWorkSchedule(@Param("workScheduleId") UUID workScheduleId);
}

interface JpaWeekdayScheduleModelsRepository extends JpaRepository<WeekdayScheduleModel, UUID> {
  @Modifying
  @Query(value = "DELETE FROM weekday_schedules WHERE work_schedule_id = :workScheduleId", nativeQuery = true)
  void deleteManyByWorkSchedule(@Param("workScheduleId") UUID workScheduleId);

  @Query(value = "SELECT EXISTS (SELECT 1 FROM weekday_schedules WHERE time_punch_id = :timePunchId)", nativeQuery = true)
  boolean timePunchLogExists(@Param("timePunchId") UUID timePunchId);
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
  private TimePunchMapper timePunchMapper;

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
  public Pair<Array<WorkSchedule>, PlusInteger> findMany(PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var workScheduleModels = workScheduleModelsRepository.findAll(pageRequest);
    var items = workScheduleModels.stream().toList();
    var itemsCount = workScheduleModels.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, mapper::toEntity),
        PlusInteger.create((int) itemsCount, "contagem de escalas"));
  }

  @Override
  public Array<WorkSchedule> findAllWithAnyCollaborator() {
    var ids = workScheduleModelsRepository.findWorkScheduleWithAnyCollaboratorIds();
    var workScheduleModels = workScheduleModelsRepository.findAllById(ids);
    return Array.createFrom(workScheduleModels, mapper::toEntity);
  }

  @Override
  public Array<Id> findCollaboratorIdsByWorkSchedule(WorkSchedule workSchedule) {
    var ids = workScheduleModelsRepository.findCollaboratorIdsByWorkSchedule(workSchedule.getId().value());
    return Array.createFrom(ids, (id) -> Id.create(id.toString()));
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
      var timePunchModel = timePunchMapper.toModel(weekdaySchedule.getTimePunch());
      timePunchModelsRepository.save(timePunchModel);
      weekdayScheduleModel.setWorkSchedule(mapper.toModel(workSchedule));
      weekdayScheduleModel.setTimePunch(timePunchModel);
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
  @Transactional
  public void updateDaysOffSchedule(WorkSchedule workSchedule) {
    var daysOffModel = workSchedule.getDaysOffSchedule().days().map((dayOff) -> {
      var dayOffModel = dayOffMapper.toModel(dayOff);
      dayOffModel.setWorkSchedule(mapper.toModel(workSchedule));
      return dayOffModel;
    });
    dayOffModelsRepository.deleteManyByWorkSchedule(workSchedule.getId().value());
    dayOffModelsRepository.saveAll(daysOffModel.list());
  }

  @Override
  @Transactional
  public void remove(WorkSchedule workSchedule) {
    dayOffModelsRepository.deleteManyByWorkSchedule(workSchedule.getId().value());

    var timePunchIds = workSchedule.getWeekSchedule()
        .map((weekdaySchedule) -> weekdaySchedule.getTimePunch().getId().value());
    weekdayScheduleModelsRepository.deleteManyByWorkSchedule(workSchedule.getId().value());
    timePunchModelsRepository.deleteMany(timePunchIds.list());

    workScheduleModelsRepository.deleteById(workSchedule.getId().value());
  }

  @Override
  public Logical hasAnyCollaborator(Id workScheduleId) {
    return Logical.create(workScheduleModelsRepository.hasAnyCollaborator(workScheduleId.value()));
  }

  @Override
  public Logical hasTimePunchSchedule(TimePunch timePunch) {
    return Logical.create(weekdayScheduleModelsRepository.timePunchLogExists(timePunch.getId().value()));
  }

}
