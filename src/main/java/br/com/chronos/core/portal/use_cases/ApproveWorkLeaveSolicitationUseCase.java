package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.WorkLeaveSolicitation;
import br.com.chronos.core.portal.domain.events.WorkLeaveSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ApproveWorkLeaveSolicitationUseCase extends ApproveSolicitationUseCase {
  public ApproveWorkLeaveSolicitationUseCase(SolicitationsRepository repository, PortalBroker broker) {
    super(repository, broker);
  }

  public void execute(
      String solicitationId,
      ResponsibleAggregateDto replierResponsible,
      String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    approveSolicitation(solicitation, replierResponsible, feedbackMessage);
    repository.replace(solicitation);

    var event = new WorkLeaveSolicitationApprovedEvent(solicitation);
    broker.publish(event);
  }

  private WorkLeaveSolicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findWorkLeaveSolicitationById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return solicitation.get();
  }
}
