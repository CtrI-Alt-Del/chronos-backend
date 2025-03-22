package br.com.chronos.core.modules.global.domain.records.fakers;

import java.util.Random;

import br.com.chronos.core.modules.global.domain.records.CollaborationSector;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;

public class CollaborationSectorFaker {
  private static Random random = new Random();

  public static CollaborationSector fake() {
    return CollaborationSector.create(fakeDto());
  }

  public static String fakeDto() {
    var sector = Sector.values()[random.nextInt(Sector.values().length)];
    return sector.toString();
  }
}
