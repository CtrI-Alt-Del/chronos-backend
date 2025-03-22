package br.com.chronos.server.database.jpa.collaborator.mappers;

import org.springframework.stereotype.Service;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

@Service
public class CollaboratorMapper {
  public CollaboratorModel toModel(Collaborator entity) {
    var model = CollaboratorModel.builder()
        .id(entity.getId().value())
        .password(entity.getPassword().value())
        .name(entity.getName().value())
        .cpf(entity.getCpf().value())
        .sector(entity.getSector().value())
        .role(entity.getRole().value())
        .build();

    return model;
  }

  public Collaborator toEntity(CollaboratorModel model) {
    var dto = new CollaboratorDto()
        .setId(model.getId().toString())
        .setPassword(model.getPassword().toString())
        .setName(model.getName().toString())
        .setEmail(model.getAccount().getEmail().toString())
        .setCpf(model.getCpf().toString())
        .setSector(model.getSector().toString())
        .setRole(model.getRole().toString())
        .setActive(model.getAccount().getIsActive());

    return new Collaborator(dto);
  }
}
