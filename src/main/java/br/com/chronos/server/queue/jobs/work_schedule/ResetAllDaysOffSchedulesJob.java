package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.chronos.core.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.ResetAllDaysOffSchedulesUseCase;

@Component
public class ResetAllDaysOffSchedulesJob {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @Scheduled(cron = "0 0 0 1 * ?")
  public void handle() {
    var useCase = new ResetAllDaysOffSchedulesUseCase(workSchedulesRepository);
    useCase.execute();
  }
}
