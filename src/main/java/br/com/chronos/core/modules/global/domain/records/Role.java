package br.com.chronos.core.modules.global.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public record Role(RoleName value) {
  public enum RoleName {
    ADMIN,
    MANAGER,
    EMPLOYEE
  }

  public static Role create(String role) {
    if (role == null) {
      return new Role(RoleName.EMPLOYEE);
    }
    var text = Text.create(role.toUpperCase(), "cargo");
    try {
      return new Role(RoleName.valueOf(text.value()));
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser admin, gestor ou funcionário");
    }
  }

  public String toString() {
    return value.toString().toLowerCase();
  }

  public Logical isAdmin() {
    return Logical.create(value == RoleName.ADMIN);
  }

  public Logical isManager() {
    return Logical.create(value == RoleName.MANAGER);

  }

  public Logical isEmployee() {
    return Logical.create(value == RoleName.EMPLOYEE);
  }

}
