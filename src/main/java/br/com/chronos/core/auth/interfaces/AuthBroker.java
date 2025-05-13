package br.com.chronos.core.auth.interfaces;

import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;
import br.com.chronos.core.auth.domain.events.AuthenticationRequestedEvent;

public interface AuthBroker {
  public void publish(AccountUpdatedEvent event);

  public void publish(AuthenticationRequestedEvent event);
}
