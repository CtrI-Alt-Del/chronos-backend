package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.solicitation.domain.entities.PaidOvertimeSolicitation;
import br.com.chronos.core.solicitation.domain.events.PaidOvertimeSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.solicitation.interfaces.PortalBroker;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class ApprovePaidOvertimeSolicitationUseCase {
  private final SolicitationsRepository repository;
  private final PortalBroker portalBroker;

  public ApprovePaidOvertimeSolicitationUseCase(
      SolicitationsRepository repository,
      PortalBroker portalBroker) {
    this.repository = repository;
    this.portalBroker = portalBroker;
  }

  public void execute(String solicitationId, ResponsibleAggregateDto replierResponsible, String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    var feedbackText = (feedbackMessage != null)
        ? Text.create(feedbackMessage, "mensagem de feedback")
        : null;

    solicitation.approve(new ResponsibleAggregate(replierResponsible), feedbackText);
    repository.replace(solicitation);

    var event = new PaidOvertimeSolicitationApprovedEvent(solicitation.getSenderResponsible());
    portalBroker.publish(event);
  }

  private PaidOvertimeSolicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findPaidOvertimeSolicitationById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return solicitation.get();
  }
}
