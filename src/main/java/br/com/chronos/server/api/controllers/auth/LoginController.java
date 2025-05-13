package br.com.chronos.server.api.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.auth.use_cases.LoginUseCase;
import br.com.chronos.core.global.interfaces.providers.JwtProvider;
import br.com.chronos.core.notification.interfaces.CacheProvider;
import lombok.AllArgsConstructor;
import lombok.Data;

@AuthController
public class LoginController {
  @Autowired
  private AccountsRepository accountsRepository;

  @Autowired
  private CacheProvider cacheProvider;

  @Autowired
  private JwtProvider jwtProvider;

  @Data
  private static class Request {
    private String otpCode;
  }

  @Data
  @AllArgsConstructor
  private static class Response {
    private String jwt;
  }

  @PostMapping("/login")
  public ResponseEntity<Response> handle(@RequestBody Request body) {
    var useCase = new LoginUseCase(
        accountsRepository,
        cacheProvider,
        jwtProvider);
    var jwt = useCase.execute(body.getOtpCode());
    var response = new Response(jwt);
    return ResponseEntity.ok(response);
  }
}
