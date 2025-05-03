package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.ExcuseWorkdayAbsenceUseCase;

@Component
public class ExcuseWorkdayAbsenceJob {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private WorkScheduleBroker workScheduleBroker;

  public void handle(ExcusedAbsenceSolicitationApprovedEvent.Payload payload) {
    var useCase = new ExcuseWorkdayAbsenceUseCase(workdayLogsRepository, workScheduleBroker);
    useCase.execute(payload.collaboratorId(), payload.absenceDate());
  }
}
