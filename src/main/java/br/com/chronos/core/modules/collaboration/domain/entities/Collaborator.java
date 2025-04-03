package br.com.chronos.core.modules.collaboration.domain.entities;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector;
import br.com.chronos.core.modules.global.domain.records.Cpf;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.Role;
import br.com.chronos.core.modules.global.domain.records.Text;

public final class Collaborator extends Entity {
  private Text name;
  private Email email;
  private Cpf cpf;
  private Role role;
  private CollaborationSector sector;
  private Logical isActive;

  public Collaborator(CollaboratorDto dto) {
    super(dto.id);
    name = Text.create(dto.name, "nome do colaborador");
    email = Email.create(dto.email);
    cpf = Cpf.create(dto.cpf);
    role = Role.create(dto.role);
    sector = CollaborationSector.create(dto.sector);
    isActive = (dto.isActive != null) ? Logical.create(dto.isActive) : Logical.create(true);
  }

  public Logical hasSameSectorOf(Collaborator collaborator) {
    if (collaborator.getRole().isAdmin().isTrue()) {
      return Logical.createAsTrue();
    }

    return getSector().isEqual(collaborator.getSector());
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

  public Logical getIsActive() {
    return isActive;
  }

  public void update(CollaboratorDto dto) {
    if (dto.name != null) {
      this.name = Text.create(dto.name, "Collaborator name");
    }
    if (dto.email != null) {
      this.email = Email.create(dto.email);
    }
    if (dto.cpf != null) {
      this.cpf = Cpf.create(dto.cpf);
    }
    if (dto.role != null) {
      this.role = Role.create(dto.role);
    }
    if (dto.sector != null) {
      this.sector = CollaborationSector.create(dto.sector);
    }
    if (dto.isActive != null) {
      this.isActive = Logical.create(dto.isActive);
    }
  }

  public void disable() {
    this.isActive = Logical.create(false);
  }

  public void enable() {
    this.isActive = Logical.create(true);
  }

  public CollaboratorDto getDto() {
    var dto = new CollaboratorDto()
        .setId(getId().value().toString())
        .setName(getName().value().toString())
        .setEmail(getEmail().value().toString())
        .setCpf(getCpf().value().toString())
        .setRole(getRole().value().toString())
        .setSector(getSector().value().toString())
        .setActive(getIsActive().value());
    return dto;
  }
}
