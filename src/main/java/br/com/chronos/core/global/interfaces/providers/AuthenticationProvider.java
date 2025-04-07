package br.com.chronos.core.global.interfaces.providers;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;

public interface AuthenticationProvider {
  String login(AccountDto accountDto, String password);

  AccountDto register(AccountDto dto);

  Account getAuthenticatedUser();

  void logout();
}
