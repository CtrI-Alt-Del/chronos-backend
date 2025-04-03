package br.com.chronos.server.database.jpa.auth.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.server.database.jpa.auth.mappers.AccountMapper;
import br.com.chronos.server.database.jpa.auth.models.AccountModel;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

interface JpaAccountModelsRepository extends JpaRepository<AccountModel, UUID> {
  public Optional<AccountModel> findByEmail(String email);

  public Optional<AccountModel> findByCollaborator(CollaboratorModel collaborator);
}

public class JpaAccountsRepository implements AccountsRepository {
  @Autowired
  JpaAccountModelsRepository repository;

  @Autowired
  AccountMapper mapper;

  @Override
  public void add(Account account) {
    var accountModel = toModel(account);
    repository.save(accountModel);
  }

  @Override
  @Transactional
  public void addMany(Array<Account> accounts) {
    var accountModels = accounts.map(this::toModel);
    repository.saveAll(accountModels.list());
  }

  @Override
  public Optional<Account> findByEmail(Email email) {
    var collaboratorModel = repository.findByEmail(email.value().toString());
    if (collaboratorModel.isEmpty()) {
      return Optional.empty();

    }
    var account = mapper.toEntity(collaboratorModel.get());
    return Optional.of(account);
  }

  private AccountModel toModel(Account account) {
    var accountModel = mapper.toModel(account);
    if (account.isFromCollaborator().isTrue()) {
      var collaboratorModel = CollaboratorModel
          .builder()
          .id(account.getCollaboratorId().value())
          .build();
      accountModel.setCollaborator(collaboratorModel);
    }
    return accountModel;
  }

  @Override
  public Optional<Account> findByCollaborator(Id collaborationId) {
    var collaboratorModel = CollaboratorModel
        .builder()
        .id(collaborationId.value())
        .build();

    var account = repository.findByCollaborator(collaboratorModel);
    if (account.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(mapper.toEntity(account.get()));
  }

  @Override
  public void update(Account account) {
    var accountModel = mapper.toModel(account);
    var collaboratorModel = CollaboratorModel
        .builder()
        .id(account.getCollaboratorId().value())
        .build();
    accountModel.setCollaborator(collaboratorModel);
    repository.save(accountModel);
  }

}
