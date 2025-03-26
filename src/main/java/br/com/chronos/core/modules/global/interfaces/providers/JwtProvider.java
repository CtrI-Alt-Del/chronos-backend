package br.com.chronos.core.modules.global.interfaces.providers;

import br.com.chronos.core.modules.auth.domain.dtos.AccountDto;

public interface JwtProvider {
  String generateToken(AccountDto subject);
  String validateToken(String token);
}
