package br.com.chronos.core.modules.solicitation.domain.entities;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.dtos.WorkScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;

public class WorkScheduleAdjustmentSolicitation extends Solicitation {
  private Id workScheduleId;

  public WorkScheduleAdjustmentSolicitation(WorkScheduleAdjustmentSolicitationDto dto) {
    super(dto);
    workScheduleId = Id.create(dto.workScheduleId);
  }

  public WorkScheduleAdjustmentSolicitationDto getDto() {
    WorkScheduleAdjustmentSolicitationDto dto = new WorkScheduleAdjustmentSolicitationDto();
    dto.setId(getId().toString());
    dto.setDescription(getDescription().value());
    dto.setDate(getDate().value());
    dto.setStatus(getStatus().value().toString());
    dto.setFeedbackMessage(getFeedbackMessage().value());
    dto.setSenderResponsible(getSenderResponsible().getDto());
    dto.setReplierResponsible(getReplierResponsible() != null ? getReplierResponsible().getDto() : null);
    dto.setWorkScheduleId(getWorkScheduleId().value().toString());
    return dto;
  }

  public Id getWorkScheduleId() {
    return workScheduleId;
  }
}
