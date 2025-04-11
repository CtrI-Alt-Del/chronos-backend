package br.com.chronos.core.auth.domain.entities.fakers;

import java.util.UUID;

import com.github.javafaker.Faker;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.global.domain.records.fakers.CollaborationSectorFaker;
import br.com.chronos.core.global.domain.records.fakers.RoleFaker;

public class AccountFaker {
  private static Faker faker = new Faker();

  public static Account fake() {
    return new Account(fakeDto());
  }

  public static AccountDto fakeDto() {
    return new AccountDto()
        .setId(UUID.randomUUID().toString())
        .setEmail(faker.internet().emailAddress())
        .setPassword(faker.internet().password())
        .setActive(true)
        .setRole(RoleFaker.fakeDto())
        .setSector(CollaborationSectorFaker.fakeDto());
  }
}
