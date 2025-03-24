package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kotlin.Pair;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.DateRange;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.mappers.TimePunchMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkdayLogMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.TimePunchModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;

interface JpaWorkdayLogsModelsRepository extends JpaRepository<WorkdayLogModel, UUID> {

  org.springframework.data.domain.Page<WorkdayLogModel> findAllByDate(LocalDate date, PageRequest pageRequest);

  org.springframework.data.domain.Page<WorkdayLogModel> findAllByCollaboratorAndDateBetween(
      CollaboratorModel collaborator,
      LocalDate startDate,
      LocalDate endDate,
      PageRequest pageRequest);

  Optional<WorkdayLogModel> findByCollaboratorAndDate(CollaboratorModel collaborator, LocalDate Date);

  List<WorkdayLogModel> findAllByDate(LocalDate date);

  @Query(value = "SELECT EXISTS (SELECT 1 FROM work_day_logs WHERE time_punch_log_id = :timePunchId)", nativeQuery = true)
  boolean timePunchLogExists(@Param("timePunchId") UUID timePunchId);

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
  public void addMany(Array<WorkdayLog> workdayLogs) {
    Array<WorkdayLogModel> workdayLogModels = Array.createAsEmpty();
    Array<TimePunchModel> timePunchModels = Array.createAsEmpty();

    for (var workdayLog : workdayLogs.list()) {
      var workdayLogModel = mapper.toModel(workdayLog);
      var timePunchLogModel = timePunchMapper.toModel(workdayLog.getTimePunchLog());
      var timePunchScheduleModel = timePunchMapper.toModel(workdayLog.getTimePunchSchedule());

      timePunchModels.add(timePunchLogModel);
      System.out.println("timePunchModels.includes: " + timePunchModels.includes(timePunchScheduleModel).isFalse());
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
      Page page) {

    var pageRequest = PageRequest.of(page.number().value(), PaginationResponse.ITEMS_PER_PAGE);
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var workdayLogModels = repository.findAllByCollaboratorAndDateBetween(
        collaboratorModel, dateRange.startDate().value(), dateRange.endDate().value(), pageRequest);
    var items = workdayLogModels.getContent().stream().toList();
    var itemsCount = workdayLogModels.getTotalElements();
    var pageRequest = PageRequest.of(page.number().value(), PaginationResponse.ITEMS_PER_PAGE);
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var workdayLogModels = repository.findAllByCollaboratorAndDateBetween(
        collaboratorModel, dateRange.startDate().value(), dateRange.endDate().value(), pageRequest);
    var items = workdayLogModels.getContent().stream().toList();
    var itemsCount = workdayLogModels.getTotalElements();

    return new Pair<>(Array.createFrom(items, mapper::toEntity), PlusInteger.create((int) itemsCount));
    return new Pair<>(Array.createFrom(items, mapper::toEntity), PlusInteger.create((int) itemsCount));
  }

  @Override
  public Pair<Array<WorkdayLog>, PlusInteger> findManyByDate(Date date, Page page) {
    var pageRequest = PageRequest.of(page.number().value(), PaginationResponse.ITEMS_PER_PAGE);
    var workdayLogModels = repository.findAllByDate(date.value(), pageRequest);
    var items = workdayLogModels.getContent().stream().toList();
    var itemsCount = workdayLogModels.getTotalElements();

    return new Pair<>(Array.createFrom(items, mapper::toEntity), PlusInteger.create((int) itemsCount));
  }
}
