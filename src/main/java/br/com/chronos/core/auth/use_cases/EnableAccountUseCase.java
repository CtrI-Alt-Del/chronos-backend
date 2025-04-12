package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.exceptions.AccountNotFoundException;
import br.com.chronos.core.auth.domain.exceptions.NotUpdateAccountExeception;
import br.com.chronos.core.auth.domain.exceptions.ToggleSameAccountException;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.domain.records.Id;

public class EnableAccountUseCase {
  private final AccountsRepository repository;

  public EnableAccountUseCase(AccountsRepository repository) {
    this.repository = repository;
  }

  public void execute(AccountDto requesterAccountDto, String accountId) {
    var requesterAccount = new Account(requesterAccountDto);
    var account = findAccount(Id.create(accountId));

    if (requesterAccount.getRole().isAdmin().isFalse()) {
      throw new NotUpdateAccountExeception();
    }

    if (requesterAccount.equals(account)) {
      throw new ToggleSameAccountException();
    }

    account.enable();
    repository.replace(account);
  }

  private Account findAccount(Id accountId) {
    var account = repository.findById(accountId);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();

    }
    return account.get();
  }
}
