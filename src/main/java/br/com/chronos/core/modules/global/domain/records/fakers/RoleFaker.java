package br.com.chronos.core.modules.global.domain.records.fakers;

import java.util.Random;

import br.com.chronos.core.modules.global.domain.records.Role;
import br.com.chronos.core.modules.global.domain.records.Role.RoleName;

public class RoleFaker {
  private static Random random = new Random();

  public static Role fake() {
    return Role.create(fakeDto());
  }

  public static String fakeDto() {
    var roleName = RoleName.values()[random.nextInt(RoleName.values().length)];
    return roleName.toString();
  }
}
