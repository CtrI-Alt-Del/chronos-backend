package br.com.chronos.core.modules.global.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public record CollaboratorRole(Role value) {
  public enum Role {
    ADMIN,
    MANAGER,
    EMPLOYEE
  }

  public static CollaboratorRole create(String role) {
    if (role == null) {
      return new CollaboratorRole(Role.EMPLOYEE);
    }
    var text = Text.create(role.toUpperCase(), "collaborator role");
    try {
      return new CollaboratorRole(Role.valueOf(text.value()));
    } catch (Exception e) {
      throw new ValidationException(text.key(), "must be admin,manager or employee");
    }
  }

  public String toString() {
    return value.toString().toLowerCase();
  }

  public Logical isAdmin() {
    return Logical.create(value == Role.ADMIN);
  }

  public Logical isManager() {
    return Logical.create(value == Role.MANAGER);

  }

  public Logical isEmployee() {
    return Logical.create(value == Role.EMPLOYEE);
  }

}
