package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

public class LoginUseCase {
  private final AuthenticationProvider authenticationProvider;
  private final AccountsRepository accountsRepository;

  public LoginUseCase(AuthenticationProvider authenticationProvider, AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
    this.authenticationProvider = authenticationProvider;
  }

  public String execute(String email, String password) {
    var account = findAccount(email);
    var accountDto = account.getDto();
    var jwt = authenticationProvider.login(accountDto, password);
    return jwt;
  }

  private Account findAccount(String email) {
    var account = accountsRepository.findByEmail(Email.create(email));
    if (account.isEmpty()) {
      throw new NotFoundException("Conta nao encontrada");
    }
    return account.get();
  }
}
