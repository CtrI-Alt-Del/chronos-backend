package br.com.chronos.core.modules.auth.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;

public interface AccountsRepository {
  void add(Account account);

  void addMany(Array<Account> accounts);

  Optional<Account> findByCollaborator(Id CollaborationId);
  
  Optional<Account> findByEmail(Email email);

  void update(Account account);
}
