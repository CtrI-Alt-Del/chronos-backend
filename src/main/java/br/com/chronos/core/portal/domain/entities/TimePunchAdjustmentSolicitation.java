package br.com.chronos.core.portal.domain.entities;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.TimePunchAdjustmentSolicitationDto;
import br.com.chronos.core.portal.domain.records.SolicitationType;
import br.com.chronos.core.portal.domain.records.TimePunchAdjustmentReason;
import br.com.chronos.core.portal.domain.records.TimePunchAdjustmentReason.Reason;
import br.com.chronos.core.work_schedule.domain.records.TimePunchPeriod;

public final class TimePunchAdjustmentSolicitation extends Solicitation {
  private Time time;
  private TimePunchPeriod period;
  private Date workdayLogDate;
  private TimePunchAdjustmentReason reason;

  public TimePunchAdjustmentSolicitation(TimePunchAdjustmentSolicitationDto dto) {
    super(dto);
    this.type = SolicitationType.createAsTimePunchAdjusment();
    time = new Time(dto.time);
    period = TimePunchPeriod.create(dto.period);
    workdayLogDate = new Date(dto.workdayLogDate);
    reason = TimePunchAdjustmentReason.create(dto.reason);
    if (reason.value() == Reason.OTHER && dto.description == null) {
      throw new ValidationException("Razão", "Se o tipo for OUTRO e necessário descrição");

    }
  }

  public Time getTime() {
    return time;
  }

  public Date getWorkdayLogDate() {
    return workdayLogDate;
  }

  public TimePunchPeriod getPeriod() {
    return period;
  }

  public TimePunchAdjustmentReason getReason() {
    return reason;
  }

  public TimePunchAdjustmentSolicitationDto getDto() {
    TimePunchAdjustmentSolicitationDto dto = new TimePunchAdjustmentSolicitationDto();
    dto.setId(getId().toString());
    dto.setDescription(getDescription() != null ? getDescription().value() : null);
    dto.setDate(getDate().value());
    dto.setStatus(getStatus().value().toString());
    dto.setFeedbackMessage(getFeedbackMessage() != null ? getFeedbackMessage().value() : null);
    dto.setSenderResponsible(getSenderResponsible().getDto());
    dto.setReplierResponsible(getReplierResponsible() != null ? getReplierResponsible().getDto() : null);
    dto.setTime(getTime().value());
    dto.setPeriod(getPeriod().toString());
    dto.setWorkdayLogDate(getWorkdayLogDate().value());
    dto.setReason(getReason().value().toString());
    dto.setType(getType().value().toString());
    return dto;
  }

}
