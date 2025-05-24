package br.com.chronos.core.work_schedule.use_cases;

import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.events.SolicitationCreatedEvent;
import br.com.chronos.core.portal.interfaces.PortalBroker;

public class CreateSolicitationUseCase {
  protected final PortalBroker broker;

  public CreateSolicitationUseCase(PortalBroker broker) {
    this.broker = broker;
  }

  public void sendSolicitationCreatedEvent(Solicitation solicitation) {
    var event = new SolicitationCreatedEvent(solicitation);
    broker.publish(event);
  }
}
