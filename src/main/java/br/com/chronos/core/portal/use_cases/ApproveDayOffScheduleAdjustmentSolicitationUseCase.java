package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.portal.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.portal.domain.records.SolicitationType;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ApproveDayOffScheduleAdjustmentSolicitationUseCase extends ApproveSolicitationUseCase{
  public ApproveDayOffScheduleAdjustmentSolicitationUseCase(
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

    // var event = new ExcusedAbsenceSolicitationApprovedEvent(solicitation);
    // portalBroker.publish(event);
  }
  private DayOffScheduleAdjustmentSolicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findSolicitationByIdAndSolicitationType(solicitationId,
        SolicitationType.createAsDayOffSchedule());
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return (DayOffScheduleAdjustmentSolicitation) solicitation.get();
  }
}
