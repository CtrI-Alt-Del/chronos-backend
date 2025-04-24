package br.com.chronos.core.collaboration.use_cases;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.collaboration.domain.exceptions.ExistingCpfException;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Cpf;
import br.com.chronos.core.global.domain.records.Id;

public class UpdateCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  public UpdateCollaboratorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public void execute(CollaboratorDto collaboratorDto) {
    var collaborator = findCollaboratorById(Id.create(collaboratorDto.id));
    collaborator.update(collaboratorDto);

    if (collaboratorDto.cpf != null) {
      var cpf = Cpf.create(collaboratorDto.cpf);
      findExistingCollaboratorByCpf(cpf);
    }
    repository.replace(collaborator);
  }

  private Collaborator findCollaboratorById(Id collaboratorId) {
    var collaborator = repository.findById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();

    }
    return collaborator.get();
  }

  public void findExistingCollaboratorByCpf(Cpf cpf) {
    var account = this.repository.findByCpf(cpf);
    if (account.isPresent()) {
      throw new ExistingCpfException();
    }
  }
}
