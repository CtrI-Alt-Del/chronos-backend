package br.com.chronos.core.global.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.records.Role;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Cpf;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;

public class Responsible extends Entity {
  private Text name;
  private Email email;
  private Role role;
  private Cpf cpf;
  private CollaborationSector sector;

  public Responsible(ResponsibleDto dto) {
    super(dto.id);
    name = dto.name != null ? Text.create(dto.name, "nome do responsavel ") : null;
    email = dto.email != null ? Email.create(dto.email) : null;
    sector = dto.sector != null ? CollaborationSector.create(dto.sector) : null;
    cpf = dto.cpf != null ? Cpf.create(dto.cpf) : null;
    role = dto.role != null ? Role.create(dto.role) : null;
  }

  public Text getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public Cpf getCpf() {
    return cpf;
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
        .setName(getName() != null ? getName().value() : null)
        .setEmail(getEmail() != null ? getEmail().value() : null)
        .setCpf(getCpf() != null ? getCpf().value() : null)
        .setSector(getSector() != null ? getSector().toString() : null)
        .setRole(getRole() != null ? getRole().toString() : null);
  }
}
