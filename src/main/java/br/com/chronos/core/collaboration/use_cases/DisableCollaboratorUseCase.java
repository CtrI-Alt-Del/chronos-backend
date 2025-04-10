package br.com.chronos.core.collaboration.use_cases;

import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Id;

public class DisableCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  public DisableCollaboratorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId) {
    var collaborator = findCollaborator(Id.create(collaboratorId));
    collaborator.disable();
    repository.disable(collaborator);
  }

  private Collaborator findCollaborator(Id collaboratorId) {
    var collaborator = repository.findById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();

    }
    return collaborator.get();
  }

}
