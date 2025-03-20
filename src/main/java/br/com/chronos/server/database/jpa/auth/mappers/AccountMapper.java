package br.com.chronos.server.database.jpa.auth.mappers;

import org.springframework.stereotype.Service;

import br.com.chronos.core.modules.auth.domain.dtos.AccountDto;
import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

@Service
public class AccountMapper {
  public Account toAccount(CollaboratorModel model) {
    var dto = new AccountDto()
        .setId(model.getId().toString())
        .setEmail(model.getEmail().toString())
        .setPassword(model.getPassword().toString())
        .setActive(model.getIsActive())
        .setRole(model.getRole().toString())
        .setSector(model.getSector().toString());
    return new Account(dto);

  }

  public Collaborator toCollaborator(CollaboratorModel model) {
    var dto = new CollaboratorDto()
        .setId(model.getId().toString())
        .setPassword(model.getPassword().toString())
        .setName(model.getName().toString())
        .setEmail(model.getEmail().toString())
        .setCpf(model.getCpf().toString())
        .setSector(model.getSector().toString())
        .setRole(model.getRole().toString())
        .setActive(model.getIsActive());
    return new Collaborator(dto);
  }
}
