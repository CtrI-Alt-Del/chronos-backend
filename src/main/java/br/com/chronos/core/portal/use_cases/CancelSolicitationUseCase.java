package br.com.chronos.core.portal.use_cases;

import java.util.logging.Logger;

import br.com.chronos.core.auth.domain.exceptions.NotAuthorizedException;
import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class CancelSolicitationUseCase {
  private final SolicitationsRepository repository;

  public CancelSolicitationUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public void execute(
      String solicitationId,
      ResponsibleAggregateDto senderResponsible) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    if (!solicitation.getSenderResponsible().getId().value().toString().equals(senderResponsible.id)) {
      throw new NotAuthorizedException();
    }
    solicitation.cancel();
    repository.replace(solicitation);
  }

  private Solicitation findSolicitation(Id solicitationId) {
    var solicitation = repository.findById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new SolicitationNotFoundException();
    }
    return solicitation.get();
  }

}
