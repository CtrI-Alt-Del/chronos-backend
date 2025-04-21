package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.time.LocalDate;
import java.util.List;
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
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkdayLogMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;

interface JpaWorkdayLogsModelsRepository extends JpaRepository<WorkdayLogModel, UUID> {
  List<WorkdayLogModel> findAllByDate(LocalDate date);

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

  List<WorkdayLogModel> findAllByCollaboratorAndDateBetween(
      CollaboratorModel collaborator,
      LocalDate starDate,
      LocalDate endDate);

  Page<WorkdayLogModel> findAllByDateAndCollaboratorNameContainingIgnoreCaseAndCollaboratorAccountSector(
      LocalDate date,
      String collaboratorName,
      CollaborationSector.Sector collaborationSector,
      PageRequest pageRequest);

  @Query(value = "SELECT EXISTS (SELECT 1 FROM workday_logs WHERE time_punch_id = :timePunchId)", nativeQuery = true)
  boolean timePunchLogExists(@Param("timePunchId") UUID timePunchId);

  @Modifying
  @Query(value = "DELETE FROM workday_logs WHERE date = :date", nativeQuery = true)
  void deleteAllByDate(LocalDate date);
}

public class JpaWorkdayLogsRepository implements WorkdayLogsRepository {
  @Autowired
  JpaWorkdayLogsModelsRepository repository;

  @Autowired
  WorkdayLogMapper mapper;

  @Override
  public Optional<WorkdayLog> findById(Id workdayLogId) {
    var workdayLogModel = repository.findById(workdayLogId.value());
    if (workdayLogModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapper.toEntity(workdayLogModel.get()));
  }

  @Override
  public Array<WorkdayLog> findAllByDate(Date date) {
    var workdayLogModels = repository.findAllByDate(date.value());
    return Array.createFrom(workdayLogModels, mapper::toEntity);
  }

  @Override
  public Optional<WorkdayLog> findByCollaboratorAndDate(Id collaboratorId, Date date) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var workdayLogModels = repository.findByCollaboratorAndDate(collaboratorModel, date.value());

    if (workdayLogModels.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapper.toEntity(workdayLogModels.get()));
  }

  @Override
  public Array<WorkdayLog> findAllByCollaboratorAndDateRange(Id collaboratorId, DateRange dateRange) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var workdayLogModels = repository.findAllByCollaboratorAndDateBetween(
        collaboratorModel,
        dateRange.startDate().value(),
        dateRange.endDate().value());

    return Array.createFrom(workdayLogModels, mapper::toEntity);
  }

  @Override
  public void add(WorkdayLog workdayLog) {
    var workdayLogModel = mapper.toModel(workdayLog);
    repository.save(workdayLogModel);
  }

  @Override
  @Transactional
  public void addMany(Array<WorkdayLog> workdayLogs) {
    var workdayLogModels = workdayLogs.map(mapper::toModel);
    System.out.println("Workday logs saved: ");
    repository.saveAll(workdayLogModels.list());
  }

  @Override
  public Pair<Array<WorkdayLog>, PlusIntegerNumber> findManyByCollaboratorAndDateRange(
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

    return new Pair<>(Array.createFrom(items, mapper::toEntity), PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public Pair<Array<WorkdayLog>, PlusIntegerNumber> findManyByDateAndCollaborationSector(
      Date date,
      Text collaboratorName,
      CollaborationSector collaborationSector,
      PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var workdayLogModels = repository
        .findAllByDateAndCollaboratorNameContainingIgnoreCaseAndCollaboratorAccountSector(
            date.value(),
            collaboratorName.value(),
            collaborationSector.value(),
            pageRequest);
    var items = workdayLogModels.stream().toList();
    var itemsCount = workdayLogModels.getTotalElements();

    return new Pair<>(Array.createFrom(items, mapper::toEntity), PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public Logical hasTimePunch(TimePunch timePunch) {
    return Logical.create(repository.timePunchLogExists(timePunch.getId().value()));
  }

  @Override
  @Transactional
  public void removeManyByDate(Date date) {
    repository.deleteAllByDate(date.value());
  }

  @Override
  public void replaceMany(Array<WorkdayLog> workdayLogs) {
    var workdayLogModels = workdayLogs.map(mapper::toModel);
    repository.saveAll(workdayLogModels.list());
  }

  @Override
  public void replace(WorkdayLog workdayLog) {
    var workdayLogModel = mapper.toModel(workdayLog);
    repository.save(workdayLogModel);
  }

}
