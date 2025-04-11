package br.com.chronos.core.collaboration.domain.dtos;

public class CollaboratorDto {
  public String id;
  public String name;
  public String email;
  public String cpf;
  public Boolean isActive;
  public byte workload;
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

  public CollaboratorDto setWorkload(byte workload) {
    this.workload = workload;
    return this;
  }
}
