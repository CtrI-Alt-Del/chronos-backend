package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthorizedException;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;

public class DisableCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  public DisableCollaboratorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, Email collaboratorDeactivatorEmail) {
    var collaboratorDeactivator = this.repository.findByEmail(collaboratorDeactivatorEmail);
    var collaborator = findCollaborator(Id.create(collaboratorId));
    if (!collaboratorDeactivator.get().isFromSameSector(collaborator).value()) {
      throw new NotAuthorizedException();

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
