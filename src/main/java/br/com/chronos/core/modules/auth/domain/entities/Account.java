package br.com.chronos.core.modules.auth.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Role;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.auth.domain.dtos.AccountDto;
import br.com.chronos.core.modules.auth.domain.records.Password;

public final class Account extends Entity {
  private Email email;
  private Password password;
  private Logical isActive;
  private Role role;
  private Id collaboratorId;

  public Account(AccountDto dto) {
    super(dto.id);
    email = Email.create(dto.email);
    password = Password.create(dto.password);
    role = Role.create(dto.role);
    isActive = (dto.isActive != null) ? Logical.create(dto.isActive) : Logical.createAsTrue();
    collaboratorId = (dto.collaboratorId != null) ? Id.create(dto.collaboratorId) : null;
  }

  public Logical isFromCollaborator() {
    return Logical.create(collaboratorId != null);
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

  public Id getCollaboratorId() {
    return collaboratorId;
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

  public Role getRole() {
    return role;
  }

  public AccountDto getDto() {
    var dto = new AccountDto()
        .setId(getId().value().toString())
        .setEmail(getEmail().value().toString())
        .setPassword(getPassword().value().toString())
        .setActive(getIsActive().value())
        .setRole(getRole().value().toString());
    return dto;
  }
}
