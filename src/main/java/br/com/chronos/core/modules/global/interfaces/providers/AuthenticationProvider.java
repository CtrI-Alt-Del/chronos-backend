package br.com.chronos.core.modules.global.interfaces.providers;

import br.com.chronos.core.modules.auth.domain.dtos.AccountDto;
import br.com.chronos.core.modules.auth.domain.entities.Account;

public interface AuthenticationProvider {
  String login(String email, String password);

  AccountDto register(AccountDto dto);

  Account getAuthenticatedUser();

  void logout();
}
