package br.com.chronos.core.collaboration.use_cases;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.collaboration.interfaces.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Id;

public class GetCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  public GetCollaboratorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public CollaboratorDto execute(String collaboratorId) {
    var Collaborator = findCollaborator(Id.create(collaboratorId));
    return Collaborator.getDto();
  }

  private Collaborator findCollaborator(Id collaboratorId) {
    var collaborator = repository.findById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();

    }
    return collaborator.get();
  }
}
