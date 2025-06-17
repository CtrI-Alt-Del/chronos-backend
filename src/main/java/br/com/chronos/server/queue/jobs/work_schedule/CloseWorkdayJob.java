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
  // private static final String ASIA_TOKYO_ZONE_ID = "Asia/Tokyo";
  // private static final String EUROPE_LONDON_ZONE_ID = "Europe/London";
  // private static final String AMERICA_NEW_YORK_ZONE_ID = "America/New_York";
  // private static final String AUSTRALIA_SYDNEY_ZONE_ID = "Australia/Sydney";
  // private static final String AFRICA_JOHANNESBURG_ZONE_ID =
  // "Africa/Johannesburg";
  // private static final String EUROPE_PARIS_ZONE_ID = "Europe/Paris";
  // private static final String ASIA_SHANGHAI_ZONE_ID = "Asia/Shanghai";
  // private static final String ASIA_DUBAI_ZONE_ID = "Asia/Dubai";
  // private static final String AMERICA_LOS_ANGELES_ZONE_ID =
  // "America/Los_Angeles";
  // private static final String AMERICA_CHICAGO_ZONE_ID = "America/Chicago";
  // private static final String AMERICA_TORONTO_ZONE_ID = "America/Toronto";
  // private static final String EUROPE_MOSCOW_ZONE_ID = "Europe/Moscow";
  // private static final String ASIA_KOLKATA_ZONE_ID = "Asia/Kolkata";
  // private static final String PACIFIC_AUCKLAND_ZONE_ID = "Pacific/Auckland";
  // private static final String ASIA_SINGAPORE_ZONE_ID = "Asia/Singapore";
  // private static final String ASIA_HONG_KONG_ZONE_ID = "Asia/Hong_Kong";
  // private static final String AMERICA_MEXICO_CITY_ZONE_ID =
  // "America/Mexico_City";
  // private static final String AMERICA_BUENOS_AIRES_ZONE_ID =
  // "America/Buenos_Aires";
  // private static final String EUROPE_BERLIN_ZONE_ID = "Europe/Berlin";
  // private static final String EUROPE_MADRID_ZONE_ID = "Europe/Madrid";
  // private static final String EUROPE_ISTANBUL_ZONE_ID = "Europe/Istanbul";
  // private static final String AFRICA_CAIRO_ZONE_ID = "Africa/Cairo";
  // private static final String ASIA_BANGKOK_ZONE_ID = "Asia/Bangkok";
  // private static final String ASIA_SEOUL_ZONE_ID = "Asia/Seoul";
  // private static final String ASIA_TEHRAN_ZONE_ID = "Asia/Tehran";
  // private static final String ASIA_KABUL_ZONE_ID = "Asia/Kabul";
  // private static final String PACIFIC_CHATHAM_ZONE_ID = "Pacific/Chatham";
  // private static final String AMERICA_ST_JOHNS_ZONE_ID = "America/St_Johns";

  @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_SAO_PAULO_ZONE_ID)
  public void handleInAmericaSaoPaulo() {
    executeUseCase(ZoneId.of(AMERICA_SAO_PAULO_ZONE_ID));
  }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_TOKYO_ZONE_ID)
  // public void handleInAsiaTokyo() {
  // executeUseCase(ZoneId.of(ASIA_TOKYO_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = EUROPE_LONDON_ZONE_ID)
  // public void handleInEuropeLondon() {
  // executeUseCase(ZoneId.of(EUROPE_LONDON_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_NEW_YORK_ZONE_ID)
  // public void handleInAmericaNewYork() {
  // executeUseCase(ZoneId.of(AMERICA_NEW_YORK_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AUSTRALIA_SYDNEY_ZONE_ID)
  // public void handleInAustraliaSydney() {
  // executeUseCase(ZoneId.of(AUSTRALIA_SYDNEY_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AFRICA_JOHANNESBURG_ZONE_ID)
  // public void handleInAfricaJohannesburg() {
  // executeUseCase(ZoneId.of(AFRICA_JOHANNESBURG_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = EUROPE_PARIS_ZONE_ID)
  // public void handleInEuropeParis() {
  // executeUseCase(ZoneId.of(EUROPE_PARIS_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_SHANGHAI_ZONE_ID)
  // public void handleInAsiaShanghai() {
  // executeUseCase(ZoneId.of(ASIA_SHANGHAI_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_DUBAI_ZONE_ID)
  // public void handleInAsiaDubai() {
  // executeUseCase(ZoneId.of(ASIA_DUBAI_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_LOS_ANGELES_ZONE_ID)
  // public void handleInAmericaLosAngeles() {
  // executeUseCase(ZoneId.of(AMERICA_LOS_ANGELES_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_CHICAGO_ZONE_ID)
  // public void handleInAmericaChicago() {
  // executeUseCase(ZoneId.of(AMERICA_CHICAGO_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_TORONTO_ZONE_ID)
  // public void handleInAmericaToronto() {
  // executeUseCase(ZoneId.of(AMERICA_TORONTO_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = EUROPE_MOSCOW_ZONE_ID)
  // public void handleInEuropeMoscow() {
  // executeUseCase(ZoneId.of(EUROPE_MOSCOW_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_KOLKATA_ZONE_ID)
  // public void handleInAsiaKolkata() {
  // executeUseCase(ZoneId.of(ASIA_KOLKATA_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = PACIFIC_AUCKLAND_ZONE_ID)
  // public void handleInPacificAuckland() {
  // executeUseCase(ZoneId.of(PACIFIC_AUCKLAND_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_SINGAPORE_ZONE_ID)
  // public void handleInAsiaSingapore() {
  // executeUseCase(ZoneId.of(ASIA_SINGAPORE_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_HONG_KONG_ZONE_ID)
  // public void handleInAsiaHongKong() {
  // executeUseCase(ZoneId.of(ASIA_HONG_KONG_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_MEXICO_CITY_ZONE_ID)
  // public void handleInAmericaMexicoCity() {
  // executeUseCase(ZoneId.of(AMERICA_MEXICO_CITY_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_BUENOS_AIRES_ZONE_ID)
  // public void handleInAmericaBuenosAires() {
  // executeUseCase(ZoneId.of(AMERICA_BUENOS_AIRES_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = EUROPE_BERLIN_ZONE_ID)
  // public void handleInEuropeBerlin() {
  // executeUseCase(ZoneId.of(EUROPE_BERLIN_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = EUROPE_MADRID_ZONE_ID)
  // public void handleInEuropeMadrid() {
  // executeUseCase(ZoneId.of(EUROPE_MADRID_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AFRICA_CAIRO_ZONE_ID)
  // public void handleInAfricaCairo() {
  // executeUseCase(ZoneId.of(AFRICA_CAIRO_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_BANGKOK_ZONE_ID)
  // public void handleInAsiaBangkok() {
  // executeUseCase(ZoneId.of(ASIA_BANGKOK_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_SEOUL_ZONE_ID)
  // public void handleInAsiaSeoul() {
  // executeUseCase(ZoneId.of(ASIA_SEOUL_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_TEHRAN_ZONE_ID)
  // public void handleInAsiaTehran() {
  // executeUseCase(ZoneId.of(ASIA_TEHRAN_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = ASIA_KABUL_ZONE_ID)
  // public void handleInAsiaKabul() {
  // executeUseCase(ZoneId.of(ASIA_KABUL_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = PACIFIC_CHATHAM_ZONE_ID)
  // public void handleInPacificChatham() {
  // executeUseCase(ZoneId.of(PACIFIC_CHATHAM_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = AMERICA_ST_JOHNS_ZONE_ID)
  // public void handleInAmericaStJohns() {
  // executeUseCase(ZoneId.of(AMERICA_ST_JOHNS_ZONE_ID));
  // }

  // @Scheduled(cron = CRON_EXPRESSION, zone = EUROPE_ISTANBUL_ZONE_ID)
  // public void handleInEuropeIstanbul() {
  // executeUseCase(ZoneId.of(EUROPE_ISTANBUL_ZONE_ID));
  // }

  private void executeUseCase(ZoneId zoneId) {
    var useCase = new CloseWorkdayUseCase(workdayLogsRepository, workScheduleBroker);
    useCase.execute(LocalDate.now(zoneId));
  }
}