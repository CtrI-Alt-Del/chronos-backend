package br.com.chronos.core.modules.auth.use_cases;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;

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
    // i Have to password both dto ans password,with i pass only dto the password is hashed and invalid :(
    var jwt = authenticationProvider.login(accountDto,password);
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
