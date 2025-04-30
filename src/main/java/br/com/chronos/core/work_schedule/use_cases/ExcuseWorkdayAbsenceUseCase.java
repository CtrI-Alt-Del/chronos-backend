package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceExcusedEvent;
import br.com.chronos.core.work_schedule.domain.exceptions.WorkdayLogNotFoundException;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class ExcuseWorkdayAbsenceUseCase {
  private final WorkdayLogsRepository repository;
  private final WorkScheduleBroker broker;

  public ExcuseWorkdayAbsenceUseCase(
      WorkdayLogsRepository repository,
      WorkScheduleBroker broker) {
    this.repository = repository;
    this.broker = broker;
  }

  public void execute(String collaboratorId, LocalDate absenceDate) {
    var workdayLog = findWorkdayLog(Id.create(collaboratorId), Date.create(absenceDate));
    workdayLog.excuseAbsence();
    repository.replace(workdayLog);

    var event = new WorkdayAbsenceExcusedEvent(workdayLog);
    broker.publish(event);
  }

  public WorkdayLog findWorkdayLog(Id collaboratorId, Date absenceDate) {
    var workdayLog = repository.findByCollaboratorAndDate(collaboratorId, absenceDate);
    if (workdayLog.isEmpty()) {
      throw new WorkdayLogNotFoundException();
    }
    return workdayLog.get();
  }
}
