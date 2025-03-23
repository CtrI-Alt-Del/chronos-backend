package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthorizedException;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.exceptions.ExistingCpfException;
import br.com.chronos.core.modules.collaboration.domain.exceptions.ExistingEmailException;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Cpf;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;

public class CreateCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  private final AuthenticationProvider authenticationProvider;

  public CreateCollaboratorUseCase(
      CollaboratorsRepository repository,
      AuthenticationProvider authenticationProvider) {
    this.repository = repository;
    this.authenticationProvider = authenticationProvider;
  }

  public CollaboratorDto execute(CollaboratorDto dto, Email responsibleEmail) {
    var responsible = this.repository.findByEmail(responsibleEmail);
    validateUniqueEmailAndCpf(dto);
    return null;
    // This will be refactored in the next PR
    // var collaboratorDto = authenticationProvider.register(dto);

    // var collaborator = new Collaborator(collaboratorDto);
    // if (!responsible.get().isFromSameSector(collaborator).value()) {
    // throw new NotAuthorizedException();

    // }

    // repository.add(collaborator);
    // return collaborator.getDto();
  }

  private void validateUniqueEmailAndCpf(CollaboratorDto dto) {
    var existingCollaborator = repository.findByEmailOrCpf(dto.email, dto.cpf);
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
