package br.com.chronos.core.modules.auth.use_cases;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.domain.exceptions.AccountNotFoundException;
import br.com.chronos.core.modules.auth.domain.records.Password;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;

public class UpdatePasswordUseCase {
  private final AuthenticationProvider authenticationProvider;
  private final AccountsRepository repository;

  public UpdatePasswordUseCase(AuthenticationProvider authenticationProvider, AccountsRepository repository) {
    this.repository = repository;
    this.authenticationProvider = authenticationProvider;
  }

  public void execute(String collaboratorId, String password) {
    var account = findAccount(collaboratorId);
    account.updatePassword(Password.create(password));
    var accountDto = authenticationProvider.register(account.getDto());
    System.out.println(new Account(accountDto));
    repository.update(new Account(accountDto));
  }

  public Account findAccount(String collaboratorId) {
    var account = repository.findByCollaborator(Id.create(collaboratorId));
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }

    return account.get();
  }
}
