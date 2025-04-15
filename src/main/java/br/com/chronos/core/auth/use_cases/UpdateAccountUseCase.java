package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.exceptions.AccountNotFoundException;
import br.com.chronos.core.auth.domain.exceptions.NotAuthorizedException;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Role;

public class UpdateAccountUseCase {
  private final AccountsRepository accountsRepository;

  public UpdateAccountUseCase(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public void execute(AccountDto accountDto, String responsibleCollaboratorId) {
    var account = findAccount(Id.create(accountDto.collaboratorId));
    var responsibleAccount = findAccount(Id.create(responsibleCollaboratorId));
    account.updateEmail(Email.create(accountDto.email));
    account.updateRole(Role.create(accountDto.role));
    account.updateCollaborationSector(CollaborationSector.create(accountDto.collaborationSector));
    this.accountsRepository.replace(account);
  }

  public Account findAccount(Id collaboratorId) {
    var account = this.accountsRepository.findByCollaborator(collaboratorId);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return account.get();

  }
}
