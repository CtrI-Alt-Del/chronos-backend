package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.notification.use_cases.SendSolicitationDeniedEmailUseCase;
import br.com.chronos.core.portal.domain.events.SolicitationDeniedEvent;
import br.com.chronos.server.api.services.CollaborationService;

@Component
public class SendSolicitationDeniedEmailJob {
  public static final String KEY = "notification/send.solicitation.denied.email.job";

  @Autowired
  private CollaborationService collaborationService;

  @Autowired
  private EmailProvider emailProvider;

  public void handle(SolicitationDeniedEvent.Payload payload) {
    var managersEmails = collaborationService.getManagersEmails(payload.collaboratorationSector());
    var useCase = new SendSolicitationDeniedEmailUseCase(emailProvider);
    useCase.execute(managersEmails, payload.solicitationType());
  }
}
