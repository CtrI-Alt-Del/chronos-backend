package br.com.chronos.core.portal.domain.entities;

import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.PaidOvertimeSolicitationDto;
import br.com.chronos.core.portal.domain.records.SolicitationType;

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
