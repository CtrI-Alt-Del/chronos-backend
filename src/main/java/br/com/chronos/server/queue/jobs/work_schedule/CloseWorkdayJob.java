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
  public static final String KEY = "work.schedule/close.workday.job";

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private WorkScheduleBroker workScheduleBroker;

  private static final String CRON_EXPRESSION = "0 59 23 * * ?"; // Runs at 23:59 in the specified time zone
  private static final String AMERICA_SAO_PAULO_ZONE_ID = "America/Sao_Paulo";
  private static final String ASIA_TOKYO_ZONE_ID = "Asia/Tokyo";
  private static final String EUROPE_LONDON_ZONE_ID = "Europe/London";
  private static final String AMERICA_NEW_YORK_ZONE_ID = "America/New_York";
  private static final String AUSTRALIA_SYDNEY_ZONE_ID = "Australia/Sydney";
  private static final String AFRICA_JOHANNESBURG_ZONE_ID = "Africa/Johannesburg";

  @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_SAO_PAULO_ZONE_ID)
  public void handleInAmericaSaoPaulo() {
    executeUseCase(ZoneId.of(AMERICA_SAO_PAULO_ZONE_ID));
  }

  @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_TOKYO_ZONE_ID)
  public void handleInAsiaTokyo() {
    executeUseCase(ZoneId.of(ASIA_TOKYO_ZONE_ID));
  }

  @Scheduled(cron = CRON_EXPRESSION, zone = EUROPE_LONDON_ZONE_ID)
  public void handleInEuropeLondon() {
    executeUseCase(ZoneId.of(EUROPE_LONDON_ZONE_ID));
  }

  @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_NEW_YORK_ZONE_ID)
  public void handleInAmericaNewYork() {
    executeUseCase(ZoneId.of(AMERICA_NEW_YORK_ZONE_ID));
  }

  @Scheduled(cron = CRON_EXPRESSION, zone = AUSTRALIA_SYDNEY_ZONE_ID)
  public void handleInAustraliaSydney() {
    executeUseCase(ZoneId.of(AUSTRALIA_SYDNEY_ZONE_ID));
  }

  @Scheduled(cron = CRON_EXPRESSION, zone = AFRICA_JOHANNESBURG_ZONE_ID)
  public void handleInAfricaJohannesburg() {
    executeUseCase(ZoneId.of(AFRICA_JOHANNESBURG_ZONE_ID));
  }

  private void executeUseCase(ZoneId zoneId) {
    var useCase = new CloseWorkdayUseCase(workdayLogsRepository, workScheduleBroker);
    useCase.execute(LocalDate.now(zoneId));
  }
}