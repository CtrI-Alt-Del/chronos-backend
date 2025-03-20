package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthenticatedException;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Id;

public class DisableCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  public DisableCollaboratorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId,Account collaboratorDeactivator) {
    var collaborator = findCollaborator(Id.create(collaboratorId));
    if (!collaboratorDeactivator.isFromSameSector(collaborator).value()) {
      throw new NotAuthenticatedException();
      
    }
    collaborator.disable();
    repository.disable(collaborator);
  }

  private Collaborator findCollaborator(Id collaboratorId) {
    var collaborator = repository.findCollaboratorById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();

    }
    return collaborator.get();
  }

}
