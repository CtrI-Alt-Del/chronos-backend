package br.com.chronos.core.portal.domain.entities.fakers;

import java.util.Random;
import java.util.UUID;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.domain.entities.WorkLeaveSolicitation;

public class WorkLeaveSolicitationFaker {
  public static WorkLeaveSolicitation fake() {
    return new WorkLeaveSolicitation(fakeDto());
  }

  public static WorkLeaveSolicitationDto fakeDto() {
    var random = new Random();
    return new WorkLeaveSolicitationDto()
        .setId(UUID.randomUUID().toString())
        .setStartedAt(Date.createFromNow().value())
        .setEndedAt(Date.createFromNow().plusDays(10).value())
        .setDescription("Férias")
        .setJustification(null)
        .setIsVacation(random.nextBoolean());
  }
}
