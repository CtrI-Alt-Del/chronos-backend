package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.ResetAllDaysOffSchedulesUseCase;

@Component
public class ResetAllDaysOffSchedulesJob {
  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @Scheduled(cron = "0 0 0 1 * ?")
  public void handle() {
    var useCase = new ResetAllDaysOffSchedulesUseCase(dayOffSchedulesRepository);
    useCase.execute();
  }
}
