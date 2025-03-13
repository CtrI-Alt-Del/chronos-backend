package br.com.chronos.core.modules.collaboration.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Text;

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
    if (text.notEqualsTo(Role.EMPLOYEE.toString())
        && text.notEqualsTo(Role.ADMIN.toString())
        && text.notEqualsTo(Role.MANAGER.toString())) {
      throw new ValidationException("collaborator role", "must be admin,manager or employee");
    }
    return new CollaboratorRole(Role.valueOf(text.value()));
  }

  public String toString() {
    return value.toString().toLowerCase();
  }

  public Boolean isAdmin() {
    return value == Role.ADMIN;
  }

  public Boolean isManager() {
    return value == Role.MANAGER;
  }

  public Boolean isEmployee() {
    return value == Role.EMPLOYEE;
  }

}
