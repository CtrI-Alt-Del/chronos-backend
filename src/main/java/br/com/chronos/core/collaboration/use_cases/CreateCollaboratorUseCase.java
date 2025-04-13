package br.com.chronos.core.collaboration.use_cases;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
import br.com.chronos.core.collaboration.domain.exceptions.ExistingCpfException;
import br.com.chronos.core.collaboration.domain.exceptions.ExistingEmailException;
import br.com.chronos.core.collaboration.interfaces.CollaborationBroker;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Cpf;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Password;

public class CreateCollaboratorUseCase {
  private final CollaboratorsRepository collaboratorsRepository;
  private final CollaborationBroker collaborationBroker;

  public CreateCollaboratorUseCase(
      CollaboratorsRepository collaboratorsRepository,
      CollaborationBroker collaborationBroker) {
    this.collaboratorsRepository = collaboratorsRepository;
    this.collaborationBroker = collaborationBroker;
  }

  public String execute(CollaboratorDto dto, String password) {
    var accountPassword = Password.create(password);
    validateUniqueEmailAndCpf(dto);

    var collaborator = new Collaborator(dto);
    collaboratorsRepository.add(collaborator);

    var event = new CollaboratorCreatedEvent(collaborator, accountPassword);
    collaborationBroker.publish(event);
    return collaborator.getId().toString();
  }

  private void validateUniqueEmailAndCpf(CollaboratorDto dto) {
    var existingCollaborator = collaboratorsRepository.findByEmailOrCpf(dto.email, dto.cpf);
    Email email = Email.create(dto.email);
    Cpf cpf = Cpf.create(dto.cpf);

    if (existingCollaborator.isEmpty()) {
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
