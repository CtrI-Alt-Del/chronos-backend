package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.TimePunchAdjustmentSolicitation;
import br.com.chronos.core.portal.domain.events.TimePunchAdjusmentSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ApproveTimePunchAdjustmentSolicitationUseCase extends ApproveSolicitationUseCase {
  public ApproveTimePunchAdjustmentSolicitationUseCase(
      SolicitationsRepository repository,
      PortalBroker portalBroker) {
    super(repository, portalBroker);
  }

  public void execute(
      String solicitationId,
      ResponsibleAggregateDto replierResponsible,
      String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    approveSolicitation(solicitation, replierResponsible, feedbackMessage);
    repository.replace(solicitation);

    var event = new TimePunchAdjusmentSolicitationApprovedEvent(solicitation);
    portalBroker.publish(event);
  }

  private TimePunchAdjustmentSolicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findTimePunchAdjustmentSolicitationById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return solicitation.get();
  }
}
