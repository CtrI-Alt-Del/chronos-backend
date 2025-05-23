package br.com.chronos.core.auth.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Id;

public interface AccountsRepository {
  Optional<Account> findById(Id accountId);

  Optional<Account> findByCollaborator(Id CollaborationId);

  Optional<Account> findByEmail(Email email);

  void add(Account account);

  void addMany(Array<Account> accounts);

  void replace(Account account);
}
