package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayStatusChartoDto;
import br.com.chronos.core.work_schedule.domain.dtos.YearlyAbsenceChartDto;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.records.MonthlyAbsence;
import br.com.chronos.core.work_schedule.domain.records.WorkdayStatus.WorkdayStatusName;
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
  public Optional<WorkdayLog> findByDate(Date date) {
    var workdayLogModel = dao.findByDate(date.value());
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

  @Override
  public WorkdayStatusChartoDto getWorkdayStatusChartByDateRange(DateRange dateRange) {
    var normalDays = dao.countByStatusAndDateBetweenOrderByDateDesc(WorkdayStatusName.NORMAL_DAY,
        dateRange.startDate().value(),
        dateRange.endDate().value());
    var vacationDays = dao.countByStatusAndDateBetweenOrderByDateDesc(WorkdayStatusName.DAY_OFF,
        dateRange.startDate().value(),
        dateRange.startDate().value());
    var withdrawDays = dao.countByStatusAndDateBetweenOrderByDateDesc(WorkdayStatusName.WORK_LEAVE,
        dateRange.startDate().value(),
        dateRange.endDate().value());
    return WorkdayStatusChartoDto.createFromValues(normalDays, vacationDays, withdrawDays);

  }

  @Override
  public YearlyAbsenceChartDto getYearlyAbsenceChart() {
    LocalDate end = LocalDate.now();
    LocalDate start = end.minusMonths(11).withDayOfMonth(1);

    List<Object[]> results = dao.countMonthlyAbsencesByRole(
        WorkdayStatusName.ABSENCE.toString(), start, end);

    Map<Integer, MonthlyAbsence> mapMonthToAbsence = new HashMap<>();

    for (Object[] row : results) {
      int month = ((Number) row[0]).intValue();
      int collaboratorAbsence = ((Number) row[1]).intValue();
      int managerAbsence = ((Number) row[2]).intValue();

      mapMonthToAbsence.put(month, MonthlyAbsence.create(collaboratorAbsence, managerAbsence));
    }

    List<MonthlyAbsence> fullList = new ArrayList<>();
    int currentMonth = start.getMonthValue();
    int endMonth = end.getMonthValue();

    while (true) {
      fullList.add(mapMonthToAbsence.getOrDefault(currentMonth, MonthlyAbsence.create(0, 0)));
      if (currentMonth == endMonth)
        break;
      currentMonth = currentMonth % 12 + 1;
    }

    return YearlyAbsenceChartDto.createFromValues(fullList);
  }
}
