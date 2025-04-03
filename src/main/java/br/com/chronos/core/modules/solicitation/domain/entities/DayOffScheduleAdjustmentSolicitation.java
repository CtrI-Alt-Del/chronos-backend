package br.com.chronos.core.modules.solicitation.domain.entities;

import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationType;
import br.com.chronos.core.modules.work_schedule.domain.entities.DayOffSchedule;

public class DayOffScheduleAdjustmentSolicitation extends Solicitation {
  private DayOffSchedule dayOffSchedule;

  public DayOffScheduleAdjustmentSolicitation(DayOffScheduleAdjustmentSolicitationDto dto) {
    super(dto);
    this.type = SolicitationType.createAsWorkSchedule();
    dayOffSchedule = new DayOffSchedule(dto.dayOffScheduleDto);
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
