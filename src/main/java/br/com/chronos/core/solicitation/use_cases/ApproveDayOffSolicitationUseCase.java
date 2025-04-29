package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.entities.DayOffSolicitation;
import br.com.chronos.core.solicitation.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.solicitation.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;
import br.com.chronos.core.solicitation.interfaces.PortalBroker;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class ApproveDayOffSolicitationUseCase extends ApproveSolicitationUseCase {
  public ApproveDayOffSolicitationUseCase(
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

    var event = new DayOffSolicitationApprovedEvent(solicitation);
    portalBroker.publish(event);
  }

  private DayOffSolicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findSolicitationByIdAndSolicitationType(solicitationId,SolicitationType.createAsDayOff());
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return (DayOffSolicitation) solicitation.get();
  }
}
