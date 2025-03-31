package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.auth.domain.dtos.AccountDto;
import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthorizedException;
import br.com.chronos.core.modules.auth.domain.records.Password;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.exceptions.ExistingCpfException;
import br.com.chronos.core.modules.collaboration.domain.exceptions.ExistingEmailException;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Cpf;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Role;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;

public class CreateCollaboratorUseCase {
  private final AuthenticationProvider authenticationProvider;
  private final CollaboratorsRepository collaboratorsRepository;
  private final AccountsRepository accountsRepository;

  public CreateCollaboratorUseCase(
      AccountsRepository accountsRepository,
      CollaboratorsRepository collaboratorsRepository,
      AuthenticationProvider authenticationProvider) {
    this.accountsRepository = accountsRepository;
    this.collaboratorsRepository = collaboratorsRepository;
    this.authenticationProvider = authenticationProvider;
  }

  public CollaboratorDto execute(CollaboratorDto dto, Password password,
      Sector responsibleSector, Role responsibleRole) {

    validateUniqueEmailAndCpf(dto);
    var collaborator = new Collaborator(dto);

    if (collaborator.isFromSameSector(responsibleSector, responsibleRole).isFalse()) {
      throw new NotAuthorizedException();
    }
    var accountDto = new AccountDto()
        .setPassword(password.value().toString())
        .setEmail(dto.email)
        .setActive(collaborator.getIsActive().value())
        .setRole(collaborator.getRole().value().toString())
        .setSector(collaborator.getSector().value().toString())
        .setCollaboratorId(collaborator.getId().value().toString());
    var registeredAccount = authenticationProvider.register(accountDto);
    var account = new Account(registeredAccount);
    collaboratorsRepository.add(collaborator);
    accountsRepository.add(account);
    return collaborator.getDto();
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
