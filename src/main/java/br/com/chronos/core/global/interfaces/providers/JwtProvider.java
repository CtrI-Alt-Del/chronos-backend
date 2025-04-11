package br.com.chronos.core.global.interfaces.providers;

import br.com.chronos.core.auth.domain.dtos.AccountDto;

public interface JwtProvider {
  String generateToken(AccountDto subject);

  String validateToken(String token);
}
