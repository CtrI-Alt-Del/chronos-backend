package br.com.chronos.core.modules.solicitation.use_cases;

import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationStatus;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.global.domain.records.Text;
import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
public class ResolveSolicitationUseCase {
  private final SolicitationsRepository solicitationsRepository;
  private final CollaboratorsRepository collaboratorsRepository;

  public ResolveSolicitationUseCase(SolicitationsRepository solicitationsRepository,
      CollaboratorsRepository collaboratorsRepository) {
    this.solicitationsRepository = solicitationsRepository;
    this.collaboratorsRepository = collaboratorsRepository;
  }

  public SolicitationDto execute(String solicitationId, String responsibleId, String status,
      String feedbackMessage) {
    var solicitation = findSolicitation(Id.create(solicitationId));
    var responsible = findResponsible(Id.create(responsibleId));
    var solicitationStatus = SolicitationStatus.create(status);
    solicitation.status = solicitationStatus;
    if (solicitation.status.value() == SolicitationStatus.Status.PENDING) {
      throw new ValidationException("status","O status deve ser diferente de PENDENTE");
      
    }
    if (feedbackMessage != null) {
      solicitation.feedbackMessage = Text.create(feedbackMessage, "Mensagem de feedback da solicitacao");
    }
    solicitation.replierResponsible = responsible;
    solicitationsRepository.resolveSolicitation(solicitation);
    return solicitation.getDto();
  }

  private Solicitation findSolicitation(Id solicitationId) {
    var solicitation = solicitationsRepository.findSolicitationById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new NotFoundException("Solicitacao nao encontrada");
    }
    return solicitation.get();
  }

  private ResponsibleAggregate findResponsible(Id responsibleId) {
    var collaborator = collaboratorsRepository.findById(responsibleId);
    if (collaborator.isEmpty()) {
      throw new NotFoundException("Responsavel nao encontrado");
    }
    var responsibleDto = new ResponsibleDto()
        .setId(collaborator.get().getId().toString())
        .setName(collaborator.get().getName().value())
        .setEmail(collaborator.get().getEmail().value())
        .setRole(collaborator.get().getRole().value().toString());
    var responsibleAggregate = new ResponsibleAggregate(new ResponsibleAggregateDto(responsibleDto));
    return responsibleAggregate;
  }

}
