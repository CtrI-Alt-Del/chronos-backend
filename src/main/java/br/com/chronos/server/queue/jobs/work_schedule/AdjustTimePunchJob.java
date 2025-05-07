package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.events.TimePunchAdjusmentSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.AdjustTimePunchUseCase;

@Component
public class AdjustTimePunchJob {
  public static final String KEY = "work.schedule/adjust.time.punch.job";

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private WorkScheduleBroker workScheduleBroker;

  public void handle(TimePunchAdjusmentSolicitationApprovedEvent.Payload payload) {
    var useCase = new AdjustTimePunchUseCase(workdayLogsRepository, workScheduleBroker);
    useCase.execute(payload.workdayLogDate(), payload.time(), payload.period());
  }
}
