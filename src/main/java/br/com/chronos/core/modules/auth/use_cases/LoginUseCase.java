package br.com.chronos.core.modules.auth.use_cases;

import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;

public class LoginUseCase {
  private final AuthenticationProvider authenticationProvider;

  public LoginUseCase(AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
  }

  public String execute(String email, String password) {
    var jwt = authenticationProvider.login(email, password);
    return jwt;
  }
}
