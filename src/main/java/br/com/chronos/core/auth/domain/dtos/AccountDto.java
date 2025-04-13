package br.com.chronos.core.auth.domain.dtos;

public class AccountDto {
  public String id;
  public String email;
  public String password;
  public Boolean isActive;
  public String role;
  public String collaborationsector;
  public String collaboratorId;

  public AccountDto setId(String id) {
    this.id = id;
    return this;
  }

  public AccountDto setEmail(String email) {
    this.email = email;
    return this;
  }

  public AccountDto setPassword(String password) {
    this.password = password;
    return this;
  }

  public AccountDto setActive(boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public AccountDto setRole(String role) {
    this.role = role;
    return this;
  }

  public AccountDto setCollaborationSector(String collaborationsector) {
    this.collaborationsector = collaborationsector;
    return this;
  }

  public AccountDto setCollaboratorId(String collaboratorId) {
    this.collaboratorId = collaboratorId;
    return this;
  }
}
