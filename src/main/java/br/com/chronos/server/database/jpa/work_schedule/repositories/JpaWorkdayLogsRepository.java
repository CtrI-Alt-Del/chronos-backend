package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import br.com.chronos.core.work_schedule.domain.records.ClockEvent;
import br.com.chronos.core.work_schedule.domain.records.MonthlyAbsence;
import br.com.chronos.core.work_schedule.domain.records.WorkdayStatus.WorkdayStatusName;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.daos.WorkdayLogDao;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkdayLogMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;
import kotlin.Pair;
import kotlin.Triple;

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
  public Triple<Long, Long, Long> getCollaboratorsWorkdayStatusByDateRange(DateRange dateRange) {
    var normalDays = dao.countByStatusAndDateBetweenOrderByDateDesc(WorkdayStatusName.NORMAL_DAY,
        dateRange.startDate().value(),
        dateRange.endDate().value());
    var vacationDays = dao.countByStatusAndDateBetweenOrderByDateDesc(WorkdayStatusName.DAY_OFF,
        dateRange.startDate().value(),
        dateRange.endDate().value());
    var withdrawDays = dao.countByStatusAndDateBetweenOrderByDateDesc(WorkdayStatusName.WORK_LEAVE,
        dateRange.startDate().value(),
        dateRange.endDate().value());
    return new Triple<Long, Long, Long>(normalDays, vacationDays, withdrawDays);
  }

  @Override
  public Array<MonthlyAbsence> getYearlyCollaboratorsAbsence(LocalDate start, LocalDate end) {
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
    int startYear = start.getYear();
    int endYear = end.getYear();

    if (startYear == endYear) {
      for (int i = currentMonth; i <= endMonth; i++) {
        fullList.add(mapMonthToAbsence.getOrDefault(i, MonthlyAbsence.create(0, 0)));
      }
    } else {
      while (true) {
        fullList.add(mapMonthToAbsence.getOrDefault(currentMonth, MonthlyAbsence.create(0, 0)));
        if (currentMonth == 12 && startYear < endYear) {
          currentMonth = 1;
          startYear++;
        } else if (currentMonth == endMonth && startYear == endYear) {
          break;
        } else {
          currentMonth++;
        }
      }
    }

    return Array.create(fullList);
  }

  @Override
  public Array<ClockEvent> getAllTimePunchsByDate(Date date) {
    System.out.println("[DEBUG] Buscando registros para a data: " + date.value());
    List<ClockEvent> hourlyClockEvents = new ArrayList<>(24);
    for (int i = 0; i < 24; i++) {
      hourlyClockEvents.add(ClockEvent.create());
    }
    List<WorkdayLogModel> logs = dao.findAllByDate(date.value());
    System.out.println("[DEBUG] Quantidade de registros encontrados: " + logs.size());
    for (WorkdayLogModel log : logs) {
      LocalTime firstIn = log.getFirstClockIn();
      LocalTime firstOut = log.getFirstClockOut();
      LocalTime secondIn = log.getSecondClockIn();
      LocalTime secondOut = log.getSecondClockOut();
      System.out.println("[DEBUG] Registro: firstIn=" + firstIn + ", firstOut=" + firstOut + ", secondIn=" + secondIn
          + ", secondOut=" + secondOut);
      if (firstIn != null) {
        int hour = firstIn.getHour();
        hourlyClockEvents.set(hour, hourlyClockEvents.get(hour).incrementClockIns(1));
      }
      if (firstOut != null) {
        int hour = firstOut.getHour();
        hourlyClockEvents.set(hour, hourlyClockEvents.get(hour).incrementClockOuts(1));
      }
      if (secondIn != null) {
        int hour = secondIn.getHour();
        hourlyClockEvents.set(hour, hourlyClockEvents.get(hour).incrementClockIns(1));
      }
      if (secondOut != null) {
        int hour = secondOut.getHour();
        hourlyClockEvents.set(hour, hourlyClockEvents.get(hour).incrementClockOuts(1));
      }
    }
    return Array.create(hourlyClockEvents);
  }

  @Override
  public Array<Integer> getCollaboratorsQuantityWithoutPunchsFromLastSevenDays() {
    List<Object[]> rawResults = dao.countLowPunchesLast7Days();
    Map<LocalDate, Integer> countsByDate = new HashMap<>();
    for (Object[] row : rawResults) {
      LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
      int count = ((Number) row[1]).intValue();
      countsByDate.put(date, count);
    }
    Array<Integer> result = Array.createAsEmpty();
    for (int i = 6; i >= 0; i--) {
      LocalDate date = LocalDate.now().minusDays(i);
      result.add(countsByDate.getOrDefault(date, 0));
    }
    return result;
  }
}
