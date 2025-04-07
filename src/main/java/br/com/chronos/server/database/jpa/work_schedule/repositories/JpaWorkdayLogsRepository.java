package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import kotlin.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusInteger;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.mappers.TimePunchMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkdayLogMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.TimePunchModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;

interface JpaWorkdayLogsModelsRepository extends JpaRepository<WorkdayLogModel, UUID> {

  @Query("""
      SELECT wl FROM WorkdayLogModel wl
      LEFT JOIN FETCH wl.collaborator c
      WHERE c.id = :collaboratorId and wl.date BETWEEN :startDate AND :endDate
      ORDER BY wl.date DESC
      """)
  Page<WorkdayLogModel> findAllByCollaboratorAndDateBetween(
      @Param("collaboratorId") UUID collaboratorId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      PageRequest pageRequest);

  Optional<WorkdayLogModel> findByCollaboratorAndDate(CollaboratorModel collaborator, LocalDate Date);

  Page<WorkdayLogModel> findManyByDate(
      LocalDate date,
      PageRequest pageRequest);

  @Query(value = "SELECT EXISTS (SELECT 1 FROM workday_logs WHERE time_punch_log_id = :timePunchId)", nativeQuery = true)
  boolean timePunchLogExists(@Param("timePunchId") UUID timePunchId);

  @Modifying
  @Query(value = "DELETE FROM workday_logs WHERE date = :date", nativeQuery = true)
  void deleteAllByDate(@Param("date") LocalDate date);

}

public class JpaWorkdayLogsRepository implements WorkdayLogsRepository {
  @Autowired
  JpaWorkdayLogsModelsRepository repository;

  @Autowired
  WorkdayLogMapper mapper;

  @Autowired
  JpaTimePunchModelsRepository timePunchModelsRepository;

  @Autowired
  private TimePunchMapper timePunchMapper;

  @Override
  public Optional<WorkdayLog> findByCollaboratorAndDate(Id collaboratorId, Date date) {
    var workdayLogModels = repository.findByCollaboratorAndDate(
        CollaboratorModel.builder().id(collaboratorId.value()).build(), date.value());
    date.value();

    if (workdayLogModels.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapper.toEntity(workdayLogModels.get()));
  }

  @Override
  @Transactional
  public void add(WorkdayLog workdayLog) {
    var workdayLogModel = mapper.toModel(workdayLog);
    var timePunchLogModel = timePunchMapper.toModel(workdayLog.getTimePunchLog());
    var timePunchScheduleModel = timePunchMapper.toModel(workdayLog.getTimePunchSchedule());
    Array<TimePunchModel> timePunchModels = Array.createAsEmpty();

    timePunchModels.add(timePunchLogModel).add(timePunchScheduleModel);
    timePunchModelsRepository.saveAll(timePunchModels.list());

    workdayLogModel.setTimePunchLog(timePunchLogModel);
    workdayLogModel.setTimePunchSchedule(timePunchScheduleModel);
    repository.save(workdayLogModel);
  }

  @Override
  @Transactional
  public void addMany(Array<WorkdayLog> workdayLogs) {
    Array<WorkdayLogModel> workdayLogModels = Array.createAsEmpty();
    Array<TimePunchModel> timePunchModels = Array.createAsEmpty();

    for (var workdayLog : workdayLogs.list()) {
      var workdayLogModel = mapper.toModel(workdayLog);
      var timePunchLogModel = timePunchMapper.toModel(workdayLog.getTimePunchLog());
      var timePunchScheduleModel = timePunchMapper.toModel(workdayLog.getTimePunchSchedule());

      timePunchModels.add(timePunchLogModel);
      if (timePunchModels.includes(timePunchScheduleModel).isFalse()) {
        timePunchModels.add(timePunchScheduleModel);
      }

      workdayLogModel.setTimePunchLog(timePunchLogModel);
      workdayLogModel.setTimePunchSchedule(timePunchScheduleModel);
      workdayLogModels.add(workdayLogModel);
    }
    timePunchModelsRepository.saveAll(timePunchModels.list());
    repository.saveAll(workdayLogModels.list());
  }

  @Override
  public Pair<Array<WorkdayLog>, PlusInteger> findManyByCollaboratorAndDateRange(
      Id collaboratorId,
      DateRange dateRange,
      PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var workdayLogModels = repository.findAllByCollaboratorAndDateBetween(
        collaboratorId.value(),
        dateRange.startDate().value(),
        dateRange.endDate().value(),
        pageRequest);
    var items = workdayLogModels.stream().toList();
    var itemsCount = workdayLogModels.getTotalElements();

    return new Pair<>(Array.createFrom(items, mapper::toEntity), PlusInteger.create((int) itemsCount));
  }

  @Override
  public Pair<Array<WorkdayLog>, PlusInteger> findManyByDateAndCollaborationSector(
      Date date,
      CollaborationSector sector,
      PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var workdayLogModels = repository.findManyByDate(
        date.value(),
        pageRequest);
    var items = workdayLogModels.stream().toList();
    var itemsCount = workdayLogModels.getTotalElements();

    return new Pair<>(Array.createFrom(items, mapper::toEntity), PlusInteger.create((int) itemsCount));
  }

  @Override
  public Logical hasTimePunchLog(TimePunch timePunch) {
    return Logical.create(repository.timePunchLogExists(timePunch.getId().value()));
  }

  @Override
  @Transactional
  public void removeManyByDate(Date date) {
    repository.deleteAllByDate(date.value());
  }
}
