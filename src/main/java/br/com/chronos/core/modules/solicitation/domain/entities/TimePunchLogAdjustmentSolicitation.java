package br.com.chronos.core.modules.solicitation.domain.entities;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Time;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.records.TimePunchAdjustmentReason;
import br.com.chronos.core.modules.solicitation.domain.records.TimePunchAdjustmentReason.Reason;
import br.com.chronos.core.modules.work_schedule.domain.records.TimePunchPeriod;

public final class TimePunchLogAdjustmentSolicitation extends Solicitation {
  private Time time;
  private TimePunchPeriod period;
  private Id workdayLogId;
  private TimePunchAdjustmentReason reason;

  public TimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitationDto dto) {
    super(dto);
    time = new Time(dto.time);
    period = TimePunchPeriod.create(dto.period);
    workdayLogId = Id.create(dto.workdayLogId);
    reason = TimePunchAdjustmentReason.create(dto.reason);
    if (reason.value() == Reason.OTHER && dto.description == null) {
      throw new ValidationException("Razao", "Se o tipo for OUTRO e necessario descricao");

    }
  }

  public Time getTime() {
    return time;
  }

  public Id getWorkdayLogId() {
    return workdayLogId;
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
    dto.setWorkdayLogId(getWorkdayLogId().toString());
    dto.setReason(getReason().value().toString());
    return dto;
  }

}
