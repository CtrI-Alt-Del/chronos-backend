package br.com.chronos.core.modules.global.domain.entities;

import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorRole;
import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorSector;
import br.com.chronos.core.modules.collaboration.domain.records.Email;
import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.records.Text;

public class Responsible extends Entity {
  private Text name;
  private Email email;
  private CollaboratorRole role;
  private CollaboratorSector sector;

  public Responsible(ResponsibleDto dto) {
    super(dto.id);
    name = Text.create(dto.name, "Collaborator name");
    email = Email.create(dto.email, "Collaborator email");
    role = CollaboratorRole.create(dto.role);
    sector = CollaboratorSector.create(dto.sector);
  }

  public Text getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public CollaboratorRole getRole() {
    return role;
  }

  public CollaboratorSector getSector() {
    return sector;
  }

  public ResponsibleDto getDto() {
    return new ResponsibleDto()
        .setId(getId().toString())
        .setName(getName().value())
        .setEmail(getEmail().value())
        .setRole(getRole().toString())
        .setSector(getSector().toString());
  }
}
