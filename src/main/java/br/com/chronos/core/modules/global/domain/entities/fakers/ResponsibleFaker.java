package br.com.chronos.core.modules.global.domain.entities.fakers;

import com.github.javafaker.Faker;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.entities.Responsible;
import br.com.chronos.core.modules.global.domain.records.fakers.CollaborationSectorFaker;
import br.com.chronos.core.modules.global.domain.records.fakers.IdFaker;
import br.com.chronos.core.modules.global.domain.records.fakers.RoleFaker;

public class ResponsibleFaker {
  private static Faker faker = new Faker();

  public static Responsible fake() {
    return new Responsible(fakeDto());
  }

  public static ResponsibleDto fakeDto() {
    return new ResponsibleDto()
        .setId(IdFaker.fakeDto())
        .setName(faker.name().fullName())
        .setEmail(faker.internet().emailAddress())
        .setRole(RoleFaker.fakeDto())
        .setSector(CollaborationSectorFaker.fakeDto());
  }
}
