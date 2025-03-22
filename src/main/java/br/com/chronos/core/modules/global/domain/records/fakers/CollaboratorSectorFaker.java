package br.com.chronos.core.modules.global.domain.records.fakers;

import java.util.Random;

import br.com.chronos.core.modules.global.domain.records.CollaboratorSector;
import br.com.chronos.core.modules.global.domain.records.CollaboratorSector.Sector;

public class CollaboratorSectorFaker {
  private static Random random = new Random();

  public static CollaboratorSector fake() {
    return CollaboratorSector.create(fakeDto());
  }

  public static String fakeDto() {
    var sector = Sector.values()[random.nextInt(Sector.values().length)];
    return sector.toString();
  }
}
