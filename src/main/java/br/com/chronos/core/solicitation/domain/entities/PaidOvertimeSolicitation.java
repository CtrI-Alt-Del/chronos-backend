package br.com.chronos.core.solicitation.domain.entities;

import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.dtos.PaidOvertimeSolicitationDto;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;

public final class PaidOvertimeSolicitation extends Solicitation {
  public PaidOvertimeSolicitation(PaidOvertimeSolicitationDto dto) {
    super(dto);
    type = SolicitationType.createPaidOvertime();
  }

  public PaidOvertimeSolicitationDto getDto() {
    var solicitationDto = super.getDto();
    PaidOvertimeSolicitationDto dto = new PaidOvertimeSolicitationDto();
    dto
        .setId(solicitationDto.id)
        .setType(solicitationDto.type)
        .setDate(solicitationDto.date)
        .setStatus(solicitationDto.status)
        .setFeedbackMessage(solicitationDto.feedbackMessage)
        .setSenderResponsible(solicitationDto.senderResponsible)
        .setReplierResponsible(solicitationDto.replierResponsible);
    return dto;
  }
}
