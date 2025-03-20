package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.global.domain.records.Cpf;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthenticatedException;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.exceptions.ExistingCpfException;
import br.com.chronos.core.modules.collaboration.domain.exceptions.ExistingEmailException;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;

public class CreateCollaboratorUseCase {
  private final CollaboratorsRepository repository;
  private final AuthenticationProvider authenticationProvider;

  public CreateCollaboratorUseCase(CollaboratorsRepository repository, AuthenticationProvider authenticationProvider) {
    this.repository = repository;
    this.authenticationProvider = authenticationProvider;
  }

  public CollaboratorDto execute(CollaboratorDto dto, Account collaboratorCreator) {
    validateUniqueEmailAndCpf(dto);
    var collaboratorDto = authenticationProvider.register(dto);
    var collaborator = new Collaborator(collaboratorDto);
    if (!collaboratorCreator.isFromSameSector(collaborator).value()) {
      throw new NotAuthenticatedException();
    }

    repository.add(collaborator);
    return collaborator.getDto();
  }

  private void validateUniqueEmailAndCpf(CollaboratorDto dto) {
    var existingCollaborator = repository.findByEmailOrCpf(dto.email, dto.cpf);
    Email email = Email.create(dto.email, "Collaborator email");
    Cpf cpf = Cpf.create(dto.cpf, "Collaborator cpf");

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
