package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

public class CreateAccountUseCase {
  private final AccountsRepository accountsRepository;
  private final AuthenticationProvider authenticationProvider;

  public CreateAccountUseCase(
      AccountsRepository accountsRepository,
      AuthenticationProvider authenticationProvider) {
    this.accountsRepository = accountsRepository;
    this.authenticationProvider = authenticationProvider;
  }

  public void execute(AccountDto accountDto) {
    var registeredAccount = authenticationProvider.register(accountDto);
    var account = new Account(registeredAccount);
    accountsRepository.add(account);
  }
}
