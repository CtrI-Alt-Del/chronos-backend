package br.com.chronos.core.portal.domain.entities;

import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.portal.domain.records.SolicitationType;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;

public final class DayOffScheduleAdjustmentSolicitation extends Solicitation {
  private DayOffSchedule dayOffSchedule;

  public DayOffScheduleAdjustmentSolicitation(DayOffScheduleAdjustmentSolicitationDto dto) {
    super(dto);
    this.type = SolicitationType.createAsDayOffSchedule();
    dayOffSchedule = new DayOffSchedule(dto.dayOffSchedule);
  }

  public DayOffScheduleAdjustmentSolicitationDto getDto() {
    DayOffScheduleAdjustmentSolicitationDto dto = new DayOffScheduleAdjustmentSolicitationDto();
    dto.setId(getId().toString());
    dto.setDescription(getDescription() != null ? getDescription().value() : null);
    dto.setDate(getDate().value());
    dto.setStatus(getStatus().value().toString());
    dto.setFeedbackMessage(getFeedbackMessage() != null ? getFeedbackMessage().value() : null);
    dto.setSenderResponsible(getSenderResponsible().getDto());
    dto.setReplierResponsible(getReplierResponsible() != null ? getReplierResponsible().getDto() : null);
    dto.setDayOffSchedule(getDayOffSchedule().getDto());
    dto.setType(getType().value().toString());
    return dto;
  }

  public DayOffSchedule getDayOffSchedule() {
    return dayOffSchedule;
  }
}
