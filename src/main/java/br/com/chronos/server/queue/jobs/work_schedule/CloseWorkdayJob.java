package br.com.chronos.server.queue.jobs.work_schedule;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.CloseWorkdayUseCase;

@Component
public class CloseWorkdayJob {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private WorkScheduleBroker workScheduleBroker;

  @Scheduled(cron = "0 59 23 * * ?")
  public void handle() {
    var useCase = new CloseWorkdayUseCase(workdayLogsRepository, workScheduleBroker);
    useCase.execute(LocalDate.now());
  }
}
