package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.notification.use_cases.SendSolicitationCreatedEmailUseCase;
import br.com.chronos.core.portal.domain.events.SolicitationApprovedEvent;

@Component
public class SendSolicitationCreatedEmailJob {
  public static final String KEY = "notification/send.solicitation.created.email.job";

  @Autowired
  private EmailProvider emailProvider;

  public void handle(SolicitationApprovedEvent.Payload payload) {
    var useCase = new SendSolicitationCreatedEmailUseCase(emailProvider);
    // useCase.execute(payload.collaboratorSector(), payload.solicitationType());
  }
}
