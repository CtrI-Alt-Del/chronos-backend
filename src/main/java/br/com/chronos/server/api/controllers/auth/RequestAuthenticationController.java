package br.com.chronos.server.api.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.auth.interfaces.AuthBroker;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.auth.use_cases.RequestAuthenticationUseCase;
import br.com.chronos.core.notification.interfaces.CacheProvider;
import lombok.Data;

@AuthController
public class RequestAuthenticationController {
  @Autowired
  private AccountsRepository accountsRepository;

  @Autowired
  private AuthBroker authBroker;

  @Autowired
  private CacheProvider cacheProvider;

  @Data
  private static class Request {
    private String email;
    private String password;
  }

  public ResponseEntity<Void> handle(@RequestBody Request body) {
    var useCase = new RequestAuthenticationUseCase(
        accountsRepository,
        cacheProvider,
        authBroker);
    useCase.execute(body.getEmail(), body.getPassword());
    return ResponseEntity.noContent().build();
  }
}
