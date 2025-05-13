package br.com.chronos.server.api.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;

import br.com.chronos.core.auth.interfaces.AuthBroker;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.auth.use_cases.RequestAuthenticationUseCase;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.notification.interfaces.CacheProvider;

@AuthController
public class RequestAuthenticationController {
  @Autowired
  private AccountsRepository accountsRepository;

  @Autowired
  private AuthBroker authBroker;

  @Autowired
  private CacheProvider cacheProvider;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Data
  private static class Request {
    private String email;
    private String password;
  }

  @PostMapping
  public ResponseEntity<Void> handle(@RequestBody Request body) {
    var useCase = new RequestAuthenticationUseCase(
        authenticationProvider,
        cacheProvider,
        accountsRepository,
        authBroker);
    useCase.execute(body.getEmail(), body.getPassword());
    return ResponseEntity.noContent().build();
  }
}
