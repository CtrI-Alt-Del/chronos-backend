package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.DayOffSolicitation;
import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.portal.domain.records.SolicitationType;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ApproveDayOffSolicitationUseCase extends ApproveSolicitationUseCase {
  public ApproveDayOffSolicitationUseCase(
      SolicitationsRepository repository,
      PortalBroker broker) {
    super(repository, broker);
  }

  public void execute(
      String solicitationId,
      ResponsibleAggregateDto replierResponsible,
      byte collaboratorWorkload,
      String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    approveSolicitation(solicitation, replierResponsible, feedbackMessage);

    var event = new DayOffSolicitationApprovedEvent(solicitation);
    broker.publish(event);
  }

  private DayOffSolicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findSolicitationByIdAndSolicitationType(
        solicitationId,
        SolicitationType.createAsDayOff());
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return (DayOffSolicitation) solicitation.get();
  }
}
