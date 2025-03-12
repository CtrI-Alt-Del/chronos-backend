package br.com.chronos.core.modules.collaboration.domain.dtos;

public class CollaboratorDto {
  public String id;
  public String name;
  public String email;
  public String password;
  public String cpf;
  public boolean isActive;
  public String role;
  public String sector;

  public CollaboratorDto setId(String id) {
    this.id = id;
    return this;
  }

  public CollaboratorDto setName(String name) {
    this.name = name;
    return this;
  }

  public CollaboratorDto setEmail(String email) {
    this.email = email;
    return this;
  }

  public CollaboratorDto setPassword(String password) {
    this.password = password;
    return this;
  }

  public CollaboratorDto setCpf(String cpf) {
    this.cpf = cpf;
    return this;
  }

  public CollaboratorDto setActive(boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public CollaboratorDto setRole(String role) {
    this.role = role;
    return this;
  }

  public CollaboratorDto setSector(String sector) {
    this.sector = sector;
    return this;
  }
}
