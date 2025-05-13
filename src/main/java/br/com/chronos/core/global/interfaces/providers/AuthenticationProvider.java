package br.com.chronos.core.global.interfaces.providers;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.global.domain.records.Logical;

public interface AuthenticationProvider {
  Logical validateCredentials(String email, String password);

  AccountDto register(AccountDto dto);

  Account getAccount();

  void logout();
}
