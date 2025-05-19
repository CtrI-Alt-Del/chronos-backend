package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.WorkleaveSolicitation;
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
  }

  private WorkleaveSolicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findWorkleaveSolicitationById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return solicitation.get();
  }
}
