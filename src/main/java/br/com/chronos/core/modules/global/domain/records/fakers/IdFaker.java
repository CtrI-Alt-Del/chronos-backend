package br.com.chronos.core.modules.global.domain.records.fakers;

import java.util.UUID;

import br.com.chronos.core.modules.global.domain.records.Id;

public class IdFaker {

  static public Id fake() {
    return Id.create(fakeDto());
  }

  static public String fakeDto() {
    return UUID.randomUUID().toString();
  }
}
