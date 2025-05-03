package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;
import kotlin.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.chronos.server.database.jpa.work_schedule.daos.WorkdayLogDao;

public class JpaWorkdayLogsRepository implements WorkdayLogsRepository {
  @Autowired
  private WorkdayLogDao dao;

  @Autowired
  private WorkdayLogMapper mapper;

  @Override
  public Optional<WorkdayLog> findById(Id workdayLogId) {
    var workdayLogModel = dao.findById(workdayLogId.value());
    if (workdayLogModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapper.toEntity(workdayLogModel.get()));
  }

  @Override
  public Array<WorkdayLog> findAllByDate(Date date) {
    var workdayLogModels = dao.findAllByDate(date.value());
    return Array.createFrom(workdayLogModels, mapper::toEntity);
  }

  @Override
  public Optional<WorkdayLog> findByCollaboratorAndDate(Id collaboratorId, Date date) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var workdayLogModels = dao.findByCollaboratorAndDate(collaboratorModel, date.value());

    if (workdayLogModels.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapper.toEntity(workdayLogModels.get()));
  }

  @Override
  public Array<WorkdayLog> findAllByCollaboratorAndDateRange(Id collaboratorId, DateRange dateRange) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var workdayLogModels = dao.findAllByCollaboratorAndDateBetweenOrderByDateDesc(
        collaboratorModel,
        dateRange.startDate().value(),
        dateRange.endDate().value());

    return Array.createFrom(workdayLogModels, mapper::toEntity);
  }

  @Override
  public void add(WorkdayLog workdayLog) {
    var workdayLogModel = mapper.toModel(workdayLog);
    dao.save(workdayLogModel);
  }

  @Override
  @Transactional
  public void addMany(Array<WorkdayLog> workdayLogs) {
    var workdayLogModels = workdayLogs.map(mapper::toModel);
    System.out.println("Workday logs saved: ");
    dao.saveAll(workdayLogModels.list());
  }

  @Override
  public Pair<Array<WorkdayLog>, PlusIntegerNumber> findManyByCollaboratorAndDateRange(
      Id collaboratorId,
      DateRange dateRange,
      PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var workdayLogModels = dao.findAllByCollaboratorAndDateBetween(
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
    var workdayLogModels = dao
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
    return Logical.create(dao.timePunchLogExists(timePunch.getId().value()));
  }

  @Override
  @Transactional
  public void removeManyByDate(Date date) {
    dao.deleteAllByDate(date.value());
  }

  @Override
  public void replaceMany(Array<WorkdayLog> workdayLogs) {
    var workdayLogModels = workdayLogs.map(mapper::toModel);
    dao.saveAll(workdayLogModels.list());
  }

  @Override
  public void replace(WorkdayLog workdayLog) {
    var workdayLogModel = mapper.toModel(workdayLog);
    dao.save(workdayLogModel);
  }

}
