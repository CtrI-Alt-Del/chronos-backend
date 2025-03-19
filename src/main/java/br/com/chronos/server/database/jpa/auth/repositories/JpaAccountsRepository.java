package br.com.chronos.server.database.jpa.auth.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.server.database.jpa.auth.mappers.AccountMapper;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

// Cant name this to JpaCollaboratorModelsRepository,spring will complain about it
interface JpaAccountModelsRepository extends JpaRepository<CollaboratorModel, UUID> {
  public Optional<CollaboratorModel> findByEmail(String email);
}

public class JpaAccountsRepository implements AccountsRepository {

  @Autowired
  JpaAccountModelsRepository repository;

  @Autowired
  AccountMapper mapper;

  @Override
  public Optional<Account> findByEmail(Email email) {
    var collaboratorModel = repository.findByEmail(email.value().toString());
    if (collaboratorModel.isEmpty()) {
      return Optional.empty();

    }
    var account = mapper.toEntity(collaboratorModel.get());
    return Optional.of(account);
  }
}
