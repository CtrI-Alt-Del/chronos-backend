package br.com.chronos.core.collaboration.use_cases;

import java.util.List;

import br.com.chronos.core.collaboration.interfaces.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.CollaborationSector;

public class GetManagersEmailsUseCase {
  private final CollaboratorsRepository repository;

  public GetManagersEmailsUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public List<String> execute(String collaboratorationSector) {
    var managers = repository.findAllManagersByCollaborationSector(
        CollaborationSector.create(collaboratorationSector));
    return managers.map(collaborator -> collaborator.getEmail().value().toString()).list();
  }
}
