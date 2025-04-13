package br.com.chronos.server.api.controllers.auth.accounts;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.auth.use_cases.DisableAccountUseCase;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

@AccountsController
public class DisableAccountController {
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private AccountsRepository repository;

  @PatchMapping("/{accountId}/disable")
  public ResponseEntity<Void> handle(@PathVariable String accountId) {
    var account = authenticationProvider.getAccount();
    var useCase = new DisableAccountUseCase(repository);
    useCase.execute(account.getDto(), accountId);
    return ResponseEntity.noContent().build();
  }
}
