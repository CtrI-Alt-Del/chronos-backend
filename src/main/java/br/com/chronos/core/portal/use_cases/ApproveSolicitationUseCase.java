package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.events.SolicitationApprovedEvent;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ApproveSolicitationUseCase {
  protected final SolicitationsRepository repository;
  protected final PortalBroker broker;

  public ApproveSolicitationUseCase(
      SolicitationsRepository repository,
      PortalBroker portalBroker) {
    this.repository = repository;
    this.broker = portalBroker;
  }

  protected void approveSolicitation(
      Solicitation solicitation,
      ResponsibleAggregateDto replierResponsibleDto,
      String feedbackMessage) {
    var feedbackText = (feedbackMessage != null)
        ? Text.create(feedbackMessage, "mensagem de feedback")
        : null;

    var approvationResponsible = new ResponsibleAggregate(replierResponsibleDto);
    solicitation.approve(approvationResponsible, feedbackText);
    repository.replace(solicitation);

    var event = new SolicitationApprovedEvent(solicitation);
    broker.publish(event);
  }
}
