package br.com.chronos.core.portal.domain.entities;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.ExcusedAbsenceSolicitationDto;
import br.com.chronos.core.portal.domain.records.SolicitationType;

public final class ExcusedAbsenceSolicitation extends Solicitation {
  private Date absenceDate;
  private Justification justification;

  public ExcusedAbsenceSolicitation(ExcusedAbsenceSolicitationDto dto) {
    super(dto);
    type = SolicitationType.createAsExcusedAbsence();
    absenceDate = Date.create(dto.absenceDate);
    justification = dto.justification != null ? new Justification(dto.justification) : null;
  }

  public ExcusedAbsenceSolicitationDto getDto() {
    var solicitationDto = super.getDto();
    ExcusedAbsenceSolicitationDto dto = new ExcusedAbsenceSolicitationDto();
    dto
        .setId(solicitationDto.id)
        .setDescription(solicitationDto.description)
        .setDate(solicitationDto.date)
        .setStatus(solicitationDto.status)
        .setFeedbackMessage(solicitationDto.feedbackMessage)
        .setSenderResponsible(solicitationDto.senderResponsible)
        .setReplierResponsible(solicitationDto.replierResponsible)
        .setAbsenceDate(getAbsenceDate().value())
        .setJustification(getJustification() != null ? getJustification().getDto() : null)
        .setType(getType().toString());
    return dto;
  }

  public Date getAbsenceDate() {
    return absenceDate;
  }

  public Justification getJustification() {
    return justification;
  }
}
