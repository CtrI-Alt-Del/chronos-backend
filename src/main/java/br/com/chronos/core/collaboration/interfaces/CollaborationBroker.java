package br.com.chronos.core.collaboration.interfaces;

import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;

public interface CollaborationBroker {
  public void publish(CollaboratorCreatedEvent event);
}
