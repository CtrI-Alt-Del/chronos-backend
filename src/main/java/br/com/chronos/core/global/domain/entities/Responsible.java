package br.com.chronos.core.global.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.records.Role;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;

public class Responsible extends Entity {
  private Text name;
  private Email email;
  private Role role;

  public Responsible(ResponsibleDto dto) {
    super(dto.id);
    name = dto.name != null ? Text.create(dto.name, "nome do responsavel ") : null;
    email = dto.email != null ? Email.create(dto.email) : null;
    role = dto.role != null ? Role.create(dto.role) : null;
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

  public ResponsibleDto getDto() {
    return new ResponsibleDto()
        .setId(getId().toString())
        .setName(getName() != null ? getName().value() : null)
        .setEmail(getEmail() != null ? getEmail().value() : null)
        .setRole(getRole() != null ? getRole().toString() : null);
  }
}
