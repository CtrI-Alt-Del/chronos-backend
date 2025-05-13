
package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.WithdrawSolicitation;
import br.com.chronos.core.portal.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ApproveWithdrawSolicitationUseCase extends ApproveSolicitationUseCase {
  public ApproveWithdrawSolicitationUseCase(
      SolicitationsRepository repository,
      PortalBroker portalBroker) {
    super(repository, portalBroker);
  }

  public void execute(
      String solicitationId,
      ResponsibleAggregateDto replierResponsibleDto,
      String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    approveSolicitation(solicitation, replierResponsibleDto, feedbackMessage);
    repository.replace(solicitation);

    // var event = new ExcusedAbsenceSolicitationApprovedEvent(solicitation);
    // portalBroker.publish(event);
  }

  private WithdrawSolicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findWithdrawSolicitationById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return solicitation.get();
  }

}
