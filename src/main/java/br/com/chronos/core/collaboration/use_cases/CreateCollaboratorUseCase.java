package br.com.chronos.core.collaboration.use_cases;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.records.Password;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.domain.exceptions.ExistingCpfException;
import br.com.chronos.core.collaboration.domain.exceptions.ExistingEmailException;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Cpf;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

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

  public String execute(CollaboratorDto dto, Password password, String collaborationSector) {
    validateUniqueEmailAndCpf(dto);

    var collaborator = new Collaborator(dto);
    var accountDto = new AccountDto()
        .setPassword(password.value().toString())
        .setEmail(dto.email)
        .setActive(collaborator.getIsActive().value())
        .setRole(collaborator.getRole().value().toString())
        .setSector(collaborationSector)
        .setCollaboratorId(collaborator.getId().value().toString());
    var registeredAccount = authenticationProvider.register(accountDto);
    var account = new Account(registeredAccount);
    collaboratorsRepository.add(collaborator);
    accountsRepository.add(account);
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
