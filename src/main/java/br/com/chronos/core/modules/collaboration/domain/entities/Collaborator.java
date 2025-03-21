package br.com.chronos.core.modules.collaboration.domain.entities;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorRole;
import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorSector;
import br.com.chronos.core.modules.collaboration.domain.records.Password;
import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Cpf;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.Text;

public final class Collaborator extends Entity {
  private Text name;
  private Email email;
  private Password password;
  private Cpf cpf;
  private CollaboratorRole role;
  private CollaboratorSector sector;
  private Logical isActive;

  public Collaborator(CollaboratorDto dto) {
    super(dto.id);
    name = Text.create(dto.name, "Collaborator name");
    email = Email.create(dto.email, "Collaborator email");
    password = Password.create(dto.password, "Collaborator password");
    cpf = Cpf.create(dto.cpf, "Collaborator cpf");
    role = CollaboratorRole.create(dto.role);
    sector = CollaboratorSector.create(dto.sector);
    isActive = (dto.isActive != null) ? Logical.create(dto.isActive) : Logical.create(true);
  }

  public Text getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public Password getPassword() {
    return password;
  }

  public Cpf getCpf() {
    return cpf;
  }

  public CollaboratorRole getRole() {
    return role;
  }

  public CollaboratorSector getSector() {
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
      this.email = Email.create(dto.email, "Collaborator email");
    }
    if (dto.password != null) {
      this.password = Password.create(dto.password, "Collaborator password");
    }
    if (dto.cpf != null) {
      this.cpf = Cpf.create(dto.cpf, "Collaborator cpf");
    }
    if (dto.role != null) {
      this.role = CollaboratorRole.create(dto.role);
    }
    if (dto.sector != null) {
      this.sector = CollaboratorSector.create(dto.sector);
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

  public Logical isFromSameSector(Collaborator otherAccount) {
    if (otherAccount == null) {
      return Logical.createAsFalse();
    }
    if (this.role.isAdmin().isTrue()) {
      return Logical.createAsTrue();
    }

    return Logical.create(this.getSector().value().equals(otherAccount.getSector().value()));
  }

  public CollaboratorDto getDto() {
    var dto = new CollaboratorDto()
        .setId(getId().value().toString())
        .setName(getName().value().toString())
        .setEmail(getEmail().value().toString())
        .setPassword(getPassword().value().toString())
        .setCpf(getCpf().value().toString())
        .setRole(getRole().value().toString())
        .setSector(getSector().value().toString())
        .setActive(getIsActive().value());
    return dto;
  }
}
