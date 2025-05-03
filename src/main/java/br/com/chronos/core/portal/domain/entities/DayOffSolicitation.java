package br.com.chronos.core.portal.domain.entities;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.portal.domain.records.SolicitationType;

public final class DayOffSolicitation extends Solicitation {
  private Date dayOff;

  public DayOffSolicitation(DayOffSolicitationDto dto) {
    super(dto);
    type = SolicitationType.createAsDayOff();
    dayOff = Date.create(dto.dayOff);
  }

  public DayOffSolicitationDto getDto() {
    var solicitationDto = super.getDto();
    DayOffSolicitationDto dto = new DayOffSolicitationDto();
    dto
        .setId(solicitationDto.id)
        .setDescription(solicitationDto.description)
        .setDate(solicitationDto.date)
        .setStatus(solicitationDto.status)
        .setFeedbackMessage(solicitationDto.feedbackMessage)
        .setSenderResponsible(solicitationDto.senderResponsible)
        .setReplierResponsible(solicitationDto.replierResponsible)
        .setDayOff(getDayOff().value())
        .setType(getType().toString());
    return dto;
  }

  public Date getDayOff() {
    return dayOff;
  }

}
