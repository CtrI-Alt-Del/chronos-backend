package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.notification.use_cases.SendSolicitationCreatedEmailUseCase;
import br.com.chronos.core.portal.domain.events.SolicitationCreatedEvent;
import br.com.chronos.server.api.services.CollaborationService;

@Component
public class SendSolicitationCreatedEmailJob {
  public static final String KEY = "notification/send.solicitation.created.email.job";

  @Autowired
  private CollaborationService collaborationService;

  @Autowired
  private EmailProvider emailProvider;

  public void handle(SolicitationCreatedEvent.Payload payload) {
    System.out.println("collaboratorationSector: " + payload.collaboratorationSector());
    var managersEmails = collaborationService.getManagersEmails(payload.collaboratorationSector());
    System.out.println("managersEmails: " + managersEmails);
    var useCase = new SendSolicitationCreatedEmailUseCase(emailProvider);
    // useCase.execute(payload.collaboratorationSector(),
    // payload.solicitationType());
  }
}
