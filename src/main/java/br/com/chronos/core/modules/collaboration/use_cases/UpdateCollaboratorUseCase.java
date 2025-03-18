package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Id;

public class UpdateCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  public UpdateCollaboratorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }
  public CollaboratorDto execute(String collaboratorId,CollaboratorDto dto){
    var collaborator = findCollaborator(Id.create(collaboratorId));
    collaborator.update(dto);
    repository.update(collaborator);
    return collaborator.getDto();
  }

  private Collaborator findCollaborator(Id collaboratorId) {
    var collaborator = repository.findCollaboratorById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();

    }
    return collaborator.get();
  }

}
