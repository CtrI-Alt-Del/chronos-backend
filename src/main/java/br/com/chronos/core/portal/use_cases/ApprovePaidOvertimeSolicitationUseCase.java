package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.PaidOvertimeSolicitation;
import br.com.chronos.core.portal.domain.events.PaidOvertimeSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ApprovePaidOvertimeSolicitationUseCase extends ApproveSolicitationUseCase {
  public ApprovePaidOvertimeSolicitationUseCase(
      SolicitationsRepository repository,
      PortalBroker portalBroker) {
    super(repository, portalBroker);
  }

  public void execute(String solicitationId, ResponsibleAggregateDto replierResponsible, String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    approveSolicitation(solicitation, replierResponsible, feedbackMessage);
    repository.replace(solicitation);

    var event = new PaidOvertimeSolicitationApprovedEvent(solicitation);
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
