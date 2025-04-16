package br.com.chronos.core.collaboration.use_cases;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.domain.events.CollaboratorUpdatedEvent;
import br.com.chronos.core.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.collaboration.domain.exceptions.ExistingCpfException;
import br.com.chronos.core.collaboration.domain.exceptions.ExistingEmailException;
import br.com.chronos.core.collaboration.interfaces.CollaborationBroker;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Cpf;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Id;

public class UpdateCollaboratorUseCase {
  private final CollaboratorsRepository repository;
  private final CollaborationBroker collaborationBroker;

  public UpdateCollaboratorUseCase(CollaboratorsRepository repository,CollaborationBroker collaborationBroker) {
    this.collaborationBroker = collaborationBroker;
    this.repository = repository;
  }

  public CollaboratorDto execute(String collaboratorId, CollaboratorDto dto,String responsibleId) {
    System.out.println("UpdateCollaboratorUseCase.execute");
    System.out.println("collaboratorId: " + collaboratorId);
    var collaborator = findCollaborator(Id.create(collaboratorId));
    validateUniqueEmailAndCpf(dto,collaboratorId);

    collaborator.update(dto);
    System.out.println("collaborato sofreu update com sucesso: " + collaborator);
    repository.replace(collaborator);

    System.out.println("responsibleId: " + responsibleId);
    var event = new CollaboratorUpdatedEvent(collaborator,Id.create(responsibleId));
    collaborationBroker.publish(event);

    return collaborator.getDto();
  }

  private Collaborator findCollaborator(Id collaboratorId) {
    var collaborator = repository.findById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();

    }
    return collaborator.get();
  }

  private void validateUniqueEmailAndCpf(CollaboratorDto dto,String currentId) {
    var existingCollaborator = repository.findByEmailOrCpf(dto.email, dto.cpf);
    Email email = dto.email != null ? Email.create(dto.email) : null;
    Cpf cpf = dto.cpf != null ? Cpf.create(dto.cpf) : null;

    if (existingCollaborator.isEmpty() || existingCollaborator.get().getId().equals(Id.create(currentId))) {
      return;
    }

    if (existingCollaborator.get().getEmail().equals(email)) {
      throw new ExistingEmailException();
    }

    if (existingCollaborator.get().getCpf().equals(cpf)) {
      throw new ExistingCpfException();
    }
  }
}
