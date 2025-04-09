package br.com.chronos.core.modules.solicitation.use_cases;

import br.com.chronos.core.modules.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Text;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationStatus;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationType;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;

public class ResolveSolicitationUseCase {
  private final SolicitationsRepository solicitationsRepository;

  public ResolveSolicitationUseCase(SolicitationsRepository solicitationsRepository) {
    this.solicitationsRepository = solicitationsRepository;
  }

  public SolicitationDto execute(String solicitationId, Id responsibleId, String status,
      String feedbackMessage, String solicitationType) {
    var solicitation = findSolicitation(Id.create(solicitationId), SolicitationType.create(solicitationType));

    var responsible = new ResponsibleAggregateDto(new ResponsibleDto()
        .setId(responsibleId.value().toString()));
    var responsibleAggregate = new ResponsibleAggregate(responsible);

    var solicitationStatus = SolicitationStatus.create(status);
    solicitation.status = solicitationStatus;

    if (solicitation.status.value() == SolicitationStatus.Status.PENDING) {
      throw new ValidationException("status", "O status deve ser diferente de PENDENTE");

    }
    if (feedbackMessage != null) {
      solicitation.feedbackMessage = Text.create(feedbackMessage, "Mensagem de feedback da solicitacao");
    }

    solicitation.replierResponsible = responsibleAggregate;
    solicitationsRepository.resolveSolicitation(solicitation);
    return solicitation.getDto();
  }

  private Solicitation findSolicitation(Id solicitationId, SolicitationType solicitationType) {
    var solicitation = solicitationsRepository.findSolicitationByIdAndSolicitationType(solicitationId,
        solicitationType);
    if (solicitation.isEmpty()) {
      throw new NotFoundException("Solicitacao nao encontrada");
    }
    return solicitation.get();
  }
}
