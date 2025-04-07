package br.com.chronos.core.solicitation.domain.entities;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;
import br.com.chronos.core.solicitation.domain.records.TimePunchAdjustmentReason;
import br.com.chronos.core.solicitation.domain.records.TimePunchAdjustmentReason.Reason;
import br.com.chronos.core.work_schedule.domain.records.TimePunchPeriod;

public final class TimePunchLogAdjustmentSolicitation extends Solicitation {
  private Time time;
  private TimePunchPeriod period;
  private Date workdayLogDate;
  private TimePunchAdjustmentReason reason;

  public TimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitationDto dto) {
    super(dto);
    this.type = SolicitationType.createAsTimePunch();
    time = new Time(dto.time);
    period = TimePunchPeriod.create(dto.period);
    workdayLogDate = new Date(dto.workdayLogDate);
    reason = TimePunchAdjustmentReason.create(dto.reason);
    if (reason.value() == Reason.OTHER && dto.description == null) {
      throw new ValidationException("Razao", "Se o tipo for OUTRO e necessario descricao");

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

  public TimePunchLogAdjustmentSolicitationDto getDto() {
    TimePunchLogAdjustmentSolicitationDto dto = new TimePunchLogAdjustmentSolicitationDto();
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
