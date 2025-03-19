package br.com.chronos.core.modules.auth.use_cases;

import br.com.chronos.core.modules.auth.domain.dtos.AccountDto;
import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.domain.exceptions.AccountNotFoundException;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.global.domain.records.Email;

public class GetAccountUseCase {


  private AccountsRepository repository;

  public GetAccountUseCase(AccountsRepository repository) {
    this.repository = repository;
  }

  public AccountDto execute(String email) {
    var Account = findAccount(Email.create(email,"Account email"));
    return Account.getDto();
  }
  public Account findAccount(Email email){
    var account = repository.findByEmail(email);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return account.get();
  }

}
