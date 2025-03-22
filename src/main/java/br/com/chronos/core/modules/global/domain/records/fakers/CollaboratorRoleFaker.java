package br.com.chronos.core.modules.global.domain.records.fakers;

import java.util.Random;

import br.com.chronos.core.modules.global.domain.records.CollaboratorRole;
import br.com.chronos.core.modules.global.domain.records.CollaboratorRole.Role;

public class CollaboratorRoleFaker {
  private static Random random = new Random();

  public static CollaboratorRole fake() {
    return CollaboratorRole.create(fakeDto());
  }

  public static String fakeDto() {
    var role = Role.values()[random.nextInt(Role.values().length)];
    return role.toString();
  }
}
