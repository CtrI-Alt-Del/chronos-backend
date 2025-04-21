package br.com.chronos.server.queue.jobs.collaboration;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.interfaces.CollaborationBroker;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.PrepareCollaboratorsForWorkUseCase;

@Component
public class PrepareCollaboratorsForWorkJob {
  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private CollaborationBroker collaborationBroker;

  @Scheduled(cron = "0 5 0 * * ?", zone = "America/Sao_Paulo")
  public void handleInAmericaSaoPaulo() {
    executeUseCase();
  }

  @Scheduled(cron = "0 5 0 * * ?", zone = "Asia/Tokyo")
  public void handleInAsiaTokyo() {
    executeUseCase();
  }

  private void executeUseCase() {
    var useCase = new PrepareCollaboratorsForWorkUseCase(collaboratorsRepository, collaborationBroker);
    useCase.execute(LocalDate.now());
  }
}
