package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.notification.use_cases.SendSolicitationDenialEmailUseCase;
import br.com.chronos.core.portal.domain.events.SolicitationDeniedEvent;

@Component
public class SendSolicitationDenialEmailJob {
  public static final String KEY = "notification/send.solicitation.denial.email.job";

  @Autowired
  private EmailProvider emailProvider;

  public void handle(SolicitationDeniedEvent.Payload payload) {
    var useCase = new SendSolicitationDenialEmailUseCase(emailProvider);
    useCase.execute(payload.employeeName(), payload.employeeEmail(), payload.solicitationType());
  }
}
