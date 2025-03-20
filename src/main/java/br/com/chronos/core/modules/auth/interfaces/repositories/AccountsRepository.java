package br.com.chronos.core.modules.auth.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.global.domain.records.Email;

public interface AccountsRepository{
  Optional<Account> findByEmail(Email email);
  Collaborator findProfile(Email email);
}
