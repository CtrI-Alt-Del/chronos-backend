package br.com.chronos.core.collaboration.domain.entities.fakers;

import java.util.List;
import java.util.UUID;

import com.github.javafaker.Faker;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.fakers.CollaborationSectorFaker;
import br.com.chronos.core.global.domain.records.fakers.RoleFaker;

public class CollaboratorFaker {
  private static Faker faker = new Faker();

  public static Collaborator fake() {
    return new Collaborator(fakeDto());
  }

  public static CollaboratorDto fakeDto() {
    var workload = faker.options().nextElement(List.of(4, 6, 8));
    return new CollaboratorDto()
        .setId(UUID.randomUUID().toString())
        .setName(faker.name().fullName())
        .setEmail(faker.internet().emailAddress())
        .setCpf(faker.number().digits(11))
        .setActive(true)
        .setRole(RoleFaker.fakeDto())
        .setWorkload((byte) (int) workload)
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
