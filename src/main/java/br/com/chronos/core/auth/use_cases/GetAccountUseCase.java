package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.exceptions.AccountNotFoundException;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.domain.records.Email;

public class GetAccountUseCase {
  private final AccountsRepository repository;

  public GetAccountUseCase(AccountsRepository repository) {
    this.repository = repository;
  }

  public AccountDto execute(String email) {
    var account = findAccount(Email.create(email));
    return account.getDto();
  }

  public Account findAccount(Email email) {
    var account = repository.findByEmail(email);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return account.get();
  }

}
