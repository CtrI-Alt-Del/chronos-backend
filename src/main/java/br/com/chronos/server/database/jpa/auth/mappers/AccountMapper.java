package br.com.chronos.server.database.jpa.auth.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.server.database.jpa.auth.models.AccountModel;

@Component
public class AccountMapper {
  public AccountModel toModel(Account entity) {
    var model = AccountModel.builder()
        .id(entity.getId().value())
        .email(entity.getEmail().value())
        .password(entity.getPassword().value())
        .isActive(entity.getIsActive().value())
        .role(entity.getRole().value())
        .sector(entity.getSector().value())
        .build();

    return model;
  }

  public Account toEntity(AccountModel model) {
    var dto = new AccountDto()
        .setId(model.getId().toString())
        .setEmail(model.getEmail().toString())
        .setPassword(model.getPassword().toString())
        .setActive(model.getIsActive())
        .setRole(model.getRole().toString())
        .setSector(model.getSector().toString())
        .setCollaboratorId(model.getCollaborator().getId().toString());

    return new Account(dto);
  }
}
