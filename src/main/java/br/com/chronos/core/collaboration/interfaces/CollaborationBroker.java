package br.com.chronos.core.collaboration.interfaces;

import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;

public interface CollaborationBroker {
  public void publish(CollaboratorCreatedEvent event);

  public void publish(CollaboratorsPreparedForWorkEvent event);
}
