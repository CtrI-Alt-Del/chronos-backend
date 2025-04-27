package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.interfaces.PortalBroker;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class ApproveSolicitationUseCase {
  protected final SolicitationsRepository repository;
  protected final PortalBroker portalBroker;

  public ApproveSolicitationUseCase(
      SolicitationsRepository repository,
      PortalBroker portalBroker) {
    this.repository = repository;
    this.portalBroker = portalBroker;
  }

  protected void approveSolicitation(
      Solicitation solicitation,
      ResponsibleAggregateDto replierResponsibleDto,
      String feedbackMessage) {
    var feedbackText = (feedbackMessage != null)
        ? Text.create(feedbackMessage, "mensagem de feedback")
        : null;

    solicitation.approve(new ResponsibleAggregate(replierResponsibleDto), feedbackText);
  }
}
