package br.com.chronos.core.solicitation.domain.entities;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.dtos.ExcuseAbsenceSolicitationDto;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;

public final class ExcuseAbsenceSolicitation extends Solicitation {
  private Date excuseAbsenceDate;
  private Justification justification;

  public ExcuseAbsenceSolicitation(ExcuseAbsenceSolicitationDto dto) {
    super(dto);
    type = SolicitationType.createAsExcuseAbsence();
    excuseAbsenceDate = Date.create(dto.excuseAbsenceDate);
    justification = dto.justification != null ? new Justification(dto.justification) : null;
  }
  public ExcuseAbsenceSolicitationDto getDto(){
    ExcuseAbsenceSolicitationDto dto = new ExcuseAbsenceSolicitationDto();
    dto.setId(getId().toString());
    dto.setDescription(getDescription() != null ? getDescription().value() : null);
    dto.setDate(getDate().value());
    dto.setStatus(getStatus().value().toString());
    dto.setFeedbackMessage(getFeedbackMessage() != null ? getFeedbackMessage().value() : null);
    dto.setSenderResponsible(getSenderResponsible().getDto());
    dto.setReplierResponsible(getReplierResponsible() != null ? getReplierResponsible().getDto() : null);
    dto.setExcuseAbsenceDate(getExcuseAbsenceDate().value());
    dto.setJustification(getJustification() != null ? getJustification().getDto() : null);
    dto.setType(getType().value().toString());
    return dto;
  }
  public Date getExcuseAbsenceDate() {
    return excuseAbsenceDate;
  }
  public Justification getJustification() {
    return justification;
  }
}

