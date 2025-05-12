package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.events.AuthenticationRequestedEvent;
import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.notification.use_cases.SendAuthenticationEmailUseCase;

@Component
public class SendAuthenticationEmailJob {
  public static final String KEY = "notification/send.authentication.email.job";

  @Autowired
  private EmailProvider emailProvider;

  public void handle(AuthenticationRequestedEvent.Payload payload) {
    var useCase = new SendAuthenticationEmailUseCase(emailProvider);
    useCase.execute(payload.accountEmail(), payload.otpCode());
  }
}
