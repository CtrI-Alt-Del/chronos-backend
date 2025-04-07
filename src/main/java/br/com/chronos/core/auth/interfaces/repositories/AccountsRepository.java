package br.com.chronos.core.auth.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Id;

public interface AccountsRepository {
  void add(Account account);

  void addMany(Array<Account> accounts);

  Optional<Account> findByCollaborator(Id CollaborationId);

  Optional<Account> findByEmail(Email email);

  void update(Account account);
}
