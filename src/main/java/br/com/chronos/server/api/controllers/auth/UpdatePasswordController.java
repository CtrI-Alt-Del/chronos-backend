package br.com.chronos.server.api.controllers.auth;

import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.auth.use_cases.UpdatePasswordUseCase;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import lombok.Data;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@AuthController
public class UpdatePasswordController {
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private AccountsRepository accountsRepository;

  @Data
  private static class Request {
    String password;
  }

  @PatchMapping("/{collaboratorId}/password")
  public ResponseEntity<Void> handle(@PathVariable String collaboratorId, @RequestBody Request body) {
    var useCase = new UpdatePasswordUseCase(authenticationProvider, accountsRepository);
    useCase.execute(collaboratorId, body.getPassword());
    return ResponseEntity.noContent().build();
  }
}
