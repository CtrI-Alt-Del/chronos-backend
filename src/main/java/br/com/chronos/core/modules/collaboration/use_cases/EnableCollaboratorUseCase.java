package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthorizedException;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;

public class EnableCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  public EnableCollaboratorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, Email responsibleEmail) {
    var responsible = this.repository.findByEmail(responsibleEmail);
    var collaborator = findCollaborator(Id.create(collaboratorId));
    if (!responsible.get().isFromSameSector(collaborator).value()) {
      throw new NotAuthorizedException();

    }

    collaborator.enable();
    repository.enable(collaborator);
  }

  private Collaborator findCollaborator(Id collaboratorId) {
    var collaborator = repository.findCollaboratorById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();
    }
    return collaborator.get();
  }

}
