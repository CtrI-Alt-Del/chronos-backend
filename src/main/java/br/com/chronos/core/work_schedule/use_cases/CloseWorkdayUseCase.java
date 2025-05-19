package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceUnexcusedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayClosedEvent;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class CloseWorkdayUseCase {
  private final WorkdayLogsRepository repository;
  private final WorkScheduleBroker broker;

  public CloseWorkdayUseCase(WorkdayLogsRepository workdayLogsRepository, WorkScheduleBroker broker) {
    this.repository = workdayLogsRepository;
    this.broker = broker;
  }

  public void execute(LocalDate date) {
    var workdayLogs = repository.findAllByDate(Date.create(date));
    Array<WorkdayLog> absences = Array.createAsEmpty();

    for (var workdayLog : workdayLogs.list()) {
      var isAbsence = workdayLog.verifyAbsense();
      if (isAbsence.isTrue()) {
        absences.add(workdayLog);
        var event = new WorkdayAbsenceUnexcusedEvent(workdayLog);
        broker.publish(event);
      }
      var event = new WorkdayClosedEvent(workdayLog);
      System.out.println("getPayload: " + event.getPayload());
      broker.publish(event);
    }

    repository.replaceMany(absences);
  }
}
