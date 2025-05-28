package br.com.chronos.core.portal.domain.entities.fakers;

import java.util.UUID;

import br.com.chronos.core.global.domain.records.fakers.DateFaker;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.domain.entities.WorkLeaveSolicitation;

public class WorkLeaveSolicitationFaker {
  public static WorkLeaveSolicitation fake() {
    return new WorkLeaveSolicitation(fakeDto());
  }

  public static WorkLeaveSolicitationDto fakeDto() {
    var startedAt = DateFaker.fake();
    var endedAt = startedAt.plusDays(7);
    return new WorkLeaveSolicitationDto()
        .setId(UUID.randomUUID().toString())
        .setDate(DateFaker.fakeDto())
        .setStatus("approved")
        .setIsVacation(false)
        .setStartedAt(startedAt.value())
        .setDescription("Minha descrição")
        .setEndedAt(endedAt.value());
  }
}
