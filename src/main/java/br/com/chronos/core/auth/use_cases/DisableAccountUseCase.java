package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.exceptions.AccountNotFoundException;
import br.com.chronos.core.auth.domain.exceptions.NotUpdateAccountExeception;
import br.com.chronos.core.auth.domain.exceptions.ToggleSameAccountException;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.domain.records.Id;

public class DisableAccountUseCase {
  private final AccountsRepository repository;

  public DisableAccountUseCase(AccountsRepository repository) {
    this.repository = repository;
  }

  public void execute(AccountDto requesterAccountDto, String collaboratorId) {
    var requesterAccount = new Account(requesterAccountDto);
    var account = findAccount(Id.create(collaboratorId));

    if (requesterAccount.equals(account)) {
      throw new ToggleSameAccountException();
    }

    if (requesterAccount.canUpdateOtherAccount(account).isFalse()) {
      throw new NotUpdateAccountExeception();
    }

    account.disable();
    repository.replace(account);
  }

  private Account findAccount(Id collaboratorId) {
    var account = repository.findByCollaborator(collaboratorId);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return account.get();
  }

}
