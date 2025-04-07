package br.com.chronos.core.collaboration.use_cases;

import java.util.List;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;

public class ListAllActiveCollaboratorsUseCase {
  private final CollaboratorsRepository repository;

  public ListAllActiveCollaboratorsUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public List<CollaboratorDto> execute() {
    var collaborators = repository.findAllActive();
    return collaborators.map(collaborator -> collaborator.getDto()).list();
  }
}
