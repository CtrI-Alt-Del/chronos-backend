package br.com.chronos.server.queue.jobs.collaboration;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.interfaces.CollaborationBroker;
import br.com.chronos.core.collaboration.interfaces.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.PrepareCollaboratorsForWorkUseCase;

@Component
public class PrepareCollaboratorsForWorkJob {
  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private CollaborationBroker collaborationBroker;

  private static final String CRON_EXPRESSION = "0 5 0 * * ?";
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
    var useCase = new PrepareCollaboratorsForWorkUseCase(collaboratorsRepository, collaborationBroker);
    useCase.execute(LocalDate.now());
  }
}
