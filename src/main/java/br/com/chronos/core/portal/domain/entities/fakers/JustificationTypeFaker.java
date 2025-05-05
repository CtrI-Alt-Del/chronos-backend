package br.com.chronos.core.portal.domain.entities.fakers;

import java.util.UUID;

import br.com.chronos.core.portal.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.portal.domain.entities.JustificationType;

public class JustificationTypeFaker {
  public static JustificationType fakeWithAttachment() {
    var dto = new JustificationTypeDto()
        .setId(UUID.randomUUID().toString())
        .setName("Atestado Médico")
        .setShouldHaveAttachment(true);
    return new JustificationType(dto);
  }

  public static JustificationType fakeWithoutAttachment() {
    var dto = new JustificationTypeDto()
        .setId(UUID.randomUUID().toString())
        .setName("Motivo Pessoal")
        .setShouldHaveAttachment(false);
    return new JustificationType(dto);
  }
}
