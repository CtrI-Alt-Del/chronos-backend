package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.exceptions.SolicitationNotFoundException;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class DenySolicitationUseCase {
  private final SolicitationsRepository repository;

  public DenySolicitationUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public void execute(
      String solicitationId,
      ResponsibleAggregateDto replierResponsible,
      String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    var feedbackText = (feedbackMessage != null)
        ? Text.create(feedbackMessage, "mensagem de feedback")
        : null;

    solicitation.deny(new ResponsibleAggregate(replierResponsible), feedbackText);
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
