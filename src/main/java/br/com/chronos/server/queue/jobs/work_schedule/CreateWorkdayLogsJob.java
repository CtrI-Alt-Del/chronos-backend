package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateWorkdayLogsUseCase;

@Component
public class CreateWorkdayLogsJob {
  public static final String KEY = "work.schedule/create.workday.logs.job";

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  public void handle(CollaboratorsPreparedForWorkEvent.Payload payload) {
    var useCase = new CreateWorkdayLogsUseCase(workdayLogsRepository, dayOffSchedulesRepository);
    useCase.execute(payload.collaboratorIds, payload.collaboratorWorkloads, payload.date);
  }
}
