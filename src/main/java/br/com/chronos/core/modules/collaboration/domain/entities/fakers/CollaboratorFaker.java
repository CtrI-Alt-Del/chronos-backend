package br.com.chronos.core.modules.collaboration.domain.entities.fakers;

import java.util.UUID;

import com.github.javafaker.Faker;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.fakers.CollaborationSectorFaker;
import br.com.chronos.core.modules.global.domain.records.fakers.RoleFaker;

public class CollaboratorFaker {
  private static Faker faker = new Faker();

  public static Collaborator fake() {
    return new Collaborator(fakeDto());
  }

  public static CollaboratorDto fakeDto() {
    return new CollaboratorDto()
        .setId(UUID.randomUUID().toString())
        .setName(faker.name().fullName())
        .setEmail(faker.internet().emailAddress())
        .setPassword(faker.internet().password())
        .setCpf(faker.number().digits(11))
        .setActive(faker.bool().bool())
        .setRole(RoleFaker.fakeDto())
        .setSector(CollaborationSectorFaker.fakeDto());
  }

  public static Array<CollaboratorDto> fakeManyDto(int count) {
    Array<CollaboratorDto> fakeCollaboratorDtos = Array.createAsEmpty();
    for (var index = 0; index < count; index++) {
      fakeCollaboratorDtos.add(fakeDto());
    }
    return fakeCollaboratorDtos;
  }
}
