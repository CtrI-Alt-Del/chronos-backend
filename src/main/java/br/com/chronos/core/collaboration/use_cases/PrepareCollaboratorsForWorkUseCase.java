package br.com.chronos.core.collaboration.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.collaboration.interfaces.CollaborationBroker;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Date;

public class PrepareCollaboratorsForWorkUseCase {
  private final CollaboratorsRepository repository;
  private final CollaborationBroker broker;

  public PrepareCollaboratorsForWorkUseCase(CollaboratorsRepository repository, CollaborationBroker broker) {
    this.repository = repository;
    this.broker = broker;
  }

  public void execute(LocalDate date) {
    var collaborators = repository.findAllActive();
    var event = new CollaboratorsPreparedForWorkEvent(collaborators, Date.create(date));
    broker.publish(event);
  }
}
