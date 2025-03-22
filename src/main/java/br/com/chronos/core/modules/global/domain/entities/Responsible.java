package br.com.chronos.core.modules.global.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector;
import br.com.chronos.core.modules.global.domain.records.Role;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Text;

public class Responsible extends Entity {
  private Text name;
  private Email email;
  private Role role;
  private CollaborationSector sector;

  public Responsible(ResponsibleDto dto) {
    super(dto.id);
    name = Text.create(dto.name, "nome do responsável");
    email = Email.create(dto.email);
    role = Role.create(dto.role);
  }

  public Text getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public Role getRole() {
    return role;
  }

  public CollaborationSector getSector() {
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
