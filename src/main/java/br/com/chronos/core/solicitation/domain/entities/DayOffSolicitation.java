package br.com.chronos.core.solicitation.domain.entities;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;

public final class DayOffSolicitation extends Solicitation {
  private Date dayOff;

  public DayOffSolicitation(DayOffSolicitationDto dto) {
    super(dto);
    type = SolicitationType.createAsDayOff();
    dayOff = Date.create(dto.dayOff);
  }

  public DayOffSolicitationDto getDto() {
    DayOffSolicitationDto dto = new DayOffSolicitationDto();
    dto.setId(getId().toString());
    dto.setDescription(getDescription() != null ? getDescription().value() : null);
    dto.setDate(getDate().value());
    dto.setStatus(getStatus().value().toString());
    dto.setFeedbackMessage(getFeedbackMessage() != null ? getFeedbackMessage().value() : null);
    dto.setSenderResponsible(getSenderResponsible().getDto());
    dto.setReplierResponsible(getReplierResponsible() != null ? getReplierResponsible().getDto() : null);
    dto.setDayOff(getDayOff().value());
    dto.setType(getType().value().toString());
    return dto;
  }

  public Date getDayOff() {
    return dayOff;
  }

}
