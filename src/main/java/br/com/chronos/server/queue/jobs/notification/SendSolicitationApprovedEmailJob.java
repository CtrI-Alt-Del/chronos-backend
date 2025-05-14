package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.notification.use_cases.SendSolicitationApprovedEmailUseCase;
import br.com.chronos.core.portal.domain.events.SolicitationApprovedEvent;
import br.com.chronos.server.api.services.CollaborationService;

@Component
public class SendSolicitationApprovedEmailJob {
  public static final String KEY = "notification/send.solicitation.approved.email.job";

  @Autowired
  private CollaborationService collaborationService;

  @Autowired
  private EmailProvider emailProvider;

  public void handle(SolicitationApprovedEvent.Payload payload) {
    var managersEmails = collaborationService.getManagersEmails(payload.collaboratorationSector());
    var useCase = new SendSolicitationApprovedEmailUseCase(emailProvider);
    useCase.execute(managersEmails, payload.solicitationType());
  }
}
