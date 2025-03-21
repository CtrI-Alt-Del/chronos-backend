package br.com.chronos.core.modules.auth.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.auth.domain.dtos.AccountDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorRole;
import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorSector;
import br.com.chronos.core.modules.collaboration.domain.records.Password;

public final class Account extends Entity {
  private Email email;
  private Password password;
  private Logical isActive;
  private CollaboratorRole role;
  private CollaboratorSector sector;

  public Account(AccountDto dto) {
    super(dto.id);
    email = Email.create(dto.email, "Collaborator email");
    password = Password.create(dto.password, "Collaborator password");
    role = CollaboratorRole.create(dto.role);
    sector = CollaboratorSector.create(dto.sector);
    isActive = (dto.isActive != null) ? Logical.create(dto.isActive) : Logical.create(true);

  }

  public Email getEmail() {
    return email;
  }

  public Password getPassword() {
    return password;
  }

  public Logical getIsActive() {
    return isActive;
  }

  public void updateEmail(Email email) {
    this.email = email;
  }

  public void updatePassword(Password password) {
    this.password = password;
  }

  public void disable() {
    this.isActive = Logical.create(false);
  }

  public void enable() {
    this.isActive = Logical.create(true);
  }

  public CollaboratorRole getRole() {
    return role;
  }

  public CollaboratorSector getSector() {
    return sector;
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

  public AccountDto getDto() {
    var dto = new AccountDto()
        .setId(getId().value().toString())
        .setEmail(getEmail().value().toString())
        .setPassword(getPassword().value().toString())
        .setActive(getIsActive().value())
        .setRole(getRole().value().toString())
        .setSector(getSector().value().toString());
    return dto;
  }
}
