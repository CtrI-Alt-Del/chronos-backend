package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ApproveVacationSolicitationUseCase extends ApproveSolicitationUseCase {
  public ApproveVacationSolicitationUseCase(
      SolicitationsRepository repository,
      PortalBroker portalBroker) {
    super(repository, portalBroker);
  }

  public void execute(
        String solicitationId,
        ResponsibleAggregateDto replierResponsible,
        byte collaboratorWorkload,
        String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));

        repository.replace(solicitation);
    
        }} 

