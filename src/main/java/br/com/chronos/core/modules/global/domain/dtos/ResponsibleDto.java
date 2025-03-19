package br.com.chronos.core.modules.global.domain.dtos;

public class ResponsibleDto {
  public String id;
  public String name;
  public String email;
  public String role;
  public String sector;

  public ResponsibleDto setId(String id) {
    this.id = id;
    return this;
  }

  public ResponsibleDto setName(String name) {
    this.name = name;
    return this;
  }

  public ResponsibleDto setEmail(String email) {
    this.email = email;
    return this;
  }

  public ResponsibleDto setRole(String role) {
    this.role = role;
    return this;
  }

  public ResponsibleDto setSector(String sector) {
    this.sector = sector;
    return this;
  }
}
