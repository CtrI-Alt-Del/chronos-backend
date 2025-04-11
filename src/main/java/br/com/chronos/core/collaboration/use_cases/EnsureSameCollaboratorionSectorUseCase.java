package br.com.chronos.core.collaboration.use_cases;

import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.collaboration.domain.exceptions.NotSameCollaborationSectorExeception;

public class EnsureSameCollaboratorionSectorUseCase {
  private final CollaboratorsRepository repository;

  public EnsureSameCollaboratorionSectorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, String otherCollaboratorId) {
    var collaborator = findCollaborator(Id.create(collaboratorId));
    var otherCollaborator = findCollaborator(Id.create(otherCollaboratorId));
    if (collaborator.hasSameSectorOf(otherCollaborator).isFalse()) {
      throw new NotSameCollaborationSectorExeception();
    }
  }

  private Collaborator findCollaborator(Id collaboratorId) {
    var collaborator = repository.findById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();
    }
    return collaborator.get();
  }

}
