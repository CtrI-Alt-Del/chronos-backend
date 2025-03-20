package br.com.chronos.core.modules.global.interfaces.providers;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;

public interface AuthenticationProvider {
  String login(String email, String password);

  CollaboratorDto register(CollaboratorDto dto);

  Account getAuthenticatedUser();

  void logout();
}
