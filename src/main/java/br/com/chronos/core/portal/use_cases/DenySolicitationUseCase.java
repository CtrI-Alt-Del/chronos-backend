package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.events.SolicitationDeniedEvent;
import br.com.chronos.core.portal.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class DenySolicitationUseCase {
  private final SolicitationsRepository repository;
  private final PortalBroker broker;

  public DenySolicitationUseCase(SolicitationsRepository repository, PortalBroker broker) {
    this.repository = repository;
    this.broker = broker;
  }

  public void execute(
      String solicitationId,
      ResponsibleAggregateDto replierResponsible,
      String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    var feedbackText = (feedbackMessage != null)
        ? Text.create(feedbackMessage, "mensagem de feedback")
        : null;

    var denyingResponsible = new ResponsibleAggregate(replierResponsible);
    solicitation.deny(denyingResponsible, feedbackText);
    repository.replace(solicitation);

    var event = new SolicitationDeniedEvent(solicitation);
    broker.publish(event);
  }

  private Solicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return solicitation.get();
  }

}
