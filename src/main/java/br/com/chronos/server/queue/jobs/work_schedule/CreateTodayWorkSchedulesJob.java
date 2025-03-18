package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.CreateTodayWorkdayLogsUseCase;

@Component
public class CreateTodayWorkSchedulesJob {

  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Scheduled(cron = "0 0 1 * * ?")
  public void handle() {
    var useCase = new CreateTodayWorkdayLogsUseCase(workSchedulesRepository, workdayLogsRepository);
    useCase.execute();
  }
}
