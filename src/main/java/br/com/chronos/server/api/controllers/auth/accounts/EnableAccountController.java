package br.com.chronos.server.api.controllers.auth.accounts;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.auth.use_cases.EnableAccountUseCase;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

@AccountsController
public class EnableAccountController {
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private AccountsRepository repository;

  @PatchMapping("/{accountId}/enable")
  public ResponseEntity<Void> handle(@PathVariable String accountId) {
    var account = authenticationProvider.getAccount();
    var useCase = new EnableAccountUseCase(repository);
    useCase.execute(account.getDto(), accountId);
    return ResponseEntity.noContent().build();
  }
}
