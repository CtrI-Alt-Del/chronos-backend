package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;

public class CreateCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  public CreateCollaboratorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public CollaboratorDto execute(CollaboratorDto dto) {
    var collaborator = new Collaborator(dto);
    repository.add(collaborator);
    return collaborator.getDto();
  }
}
