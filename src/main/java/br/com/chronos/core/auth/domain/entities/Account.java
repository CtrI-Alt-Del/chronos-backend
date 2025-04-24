package br.com.chronos.core.auth.domain.entities;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.records.Role;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Password;

public final class Account extends Entity {
  private Email email;
  private Password password;
  private Logical isActive;
  private Role role;
  private Id collaboratorId;
  private CollaborationSector collaborationSector;

  public Account(AccountDto dto) {
    super(dto.id);
    email = Email.create(dto.email);
    password = Password.create(dto.password);
    role = Role.create(dto.role);
    isActive = (dto.isActive != null) ? Logical.create(dto.isActive) : Logical.createAsTrue();
    collaborationSector = (dto.collaborationSector != null) ? CollaborationSector.create(dto.collaborationSector)
        : null;
    collaboratorId = (dto.collaboratorId != null) ? Id.create(dto.collaboratorId) : null;
  }

  public Logical canUpdateOtherAccount(Account account) {
    if (this.getRole().isAdmin().isTrue()) {
      return Logical.createAsTrue();
    }

    var hasSameCollaborationSector = this
        .getRole()
        .isManager()
        .and(this.getCollaborationSector().isEqual(account.getCollaborationSector()));

    var canUpdate = this.getRole().isAdmin().or(hasSameCollaborationSector);

    return canUpdate;
  }

  public Logical isFromCollaborator() {
    return Logical.create(collaboratorId != null);
  }

  public void disable() {
    this.isActive = Logical.create(false);
  }

  public void enable() {
    this.isActive = Logical.create(true);
  }

  public Email getEmail() {
    return email;
  }

  public Password getPassword() {
    return password;
  }

  public CollaborationSector getCollaborationSector() {
    return collaborationSector;
  }

  public Logical getIsActive() {
    return isActive;
  }

  public Id getCollaboratorId() {
    return collaboratorId;
  }

  public void update(AccountDto dto) {
    if (dto.email != null && dto.email != getEmail().value()) {
      email = Email.create(dto.email);
    }
    if (dto.role != null && dto.role != getRole().value().toString()) {
      role = Role.create(dto.role);
    }
    if (dto.collaborationSector != null && !getCollaborationSector().toString().equals(dto.collaborationSector)) {
      collaborationSector = CollaborationSector.create(dto.collaborationSector);
    }
  }

  public void updatePassword(Password password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public AccountDto getDto() {
    var dto = new AccountDto()
        .setId(getId().value().toString())
        .setEmail(getEmail().value().toString())
        .setPassword(getPassword().value().toString())
        .setActive(getIsActive().value())
        .setRole(getRole().toString());

    if (getCollaborationSector() != null) {
      dto.setCollaborationSector(getCollaborationSector().toString());
    }

    if (getCollaboratorId() != null) {
      dto.setCollaboratorId(getCollaboratorId().toString());
    }
    return dto;
  }
}
