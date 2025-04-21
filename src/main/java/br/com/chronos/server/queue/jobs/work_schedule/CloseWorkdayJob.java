package br.com.chronos.server.queue.jobs.work_schedule;

import java.time.LocalDate;
import java.time.ZoneId;

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

  private static final String CRON_EXPRESSION = "0 59 23 * * ?";
  private static final String AMERICA_SAO_PAULO_ZONE_ID = "America/Sao_Paulo";
  private static final String ASIA_TOKYO_ZONE_ID = "Asia/Tokyo";

  @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_SAO_PAULO_ZONE_ID)
  public void handleInAmericaSaoPaulo() {
    executeUseCase(ZoneId.of(AMERICA_SAO_PAULO_ZONE_ID));
  }

  @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_TOKYO_ZONE_ID)
  public void handleInAsiaTokyo() {
    executeUseCase(ZoneId.of(ASIA_TOKYO_ZONE_ID));
  }

  private void executeUseCase(ZoneId zoneId) {
    var useCase = new CloseWorkdayUseCase(workdayLogsRepository, workScheduleBroker);
    useCase.execute(LocalDate.now(zoneId));
  }
}
