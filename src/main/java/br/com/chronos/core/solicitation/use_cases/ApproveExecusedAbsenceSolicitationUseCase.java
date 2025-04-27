package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.solicitation.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.solicitation.interfaces.PortalBroker;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class ApproveExecusedAbsenceSolicitationUseCase extends ApproveSolicitationUseCase {
  public ApproveExecusedAbsenceSolicitationUseCase(
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

    var event = new ExcusedAbsenceSolicitationApprovedEvent(solicitation);
    portalBroker.publish(event);
  }

  private ExcusedAbsenceSolicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findExcusedAbsenceSolicitationById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return solicitation.get();
  }

}
