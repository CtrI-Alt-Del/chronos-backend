package br.com.chronos.core.auth.interfaces;

import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;

public interface AuthBroker {
  public void publish(AccountUpdatedEvent event);
}
