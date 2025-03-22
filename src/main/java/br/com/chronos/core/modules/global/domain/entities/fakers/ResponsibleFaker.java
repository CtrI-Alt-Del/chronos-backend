package br.com.chronos.core.modules.global.domain.entities.fakers;

import com.github.javafaker.Faker;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.entities.Responsible;
import br.com.chronos.core.modules.global.domain.records.fakers.CollaboratorRoleFaker;
import br.com.chronos.core.modules.global.domain.records.fakers.CollaboratorSectorFaker;
import br.com.chronos.core.modules.global.domain.records.fakers.IdFaker;

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
        .setRole(CollaboratorRoleFaker.fakeDto())
        .setSector(CollaboratorSectorFaker.fakeDto());
  }
}
