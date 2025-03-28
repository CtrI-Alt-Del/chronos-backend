package br.com.chronos.core.modules.solicitation.domain.entities;

import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.dtos.WorkScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;

public class WorkScheduleAdjustmentSolicitation extends Solicitation {
  private WorkSchedule workSchedule;

  public WorkScheduleAdjustmentSolicitation(WorkScheduleAdjustmentSolicitationDto dto) {
    super(dto);
    workSchedule = new WorkSchedule(dto.workScheduleDto);
  }

  public WorkScheduleAdjustmentSolicitationDto getDto() {
    WorkScheduleAdjustmentSolicitationDto dto = new WorkScheduleAdjustmentSolicitationDto();
    dto.setId(getId().toString());
    dto.setDescription(getDescription().value());
    dto.setDate(getDate().value());
    dto.setStatus(getStatus().value().toString());
    dto.setFeedbackMessage(getFeedbackMessage().value());
    dto.setSenderResponsible(getSenderResponsible().getDto());
    dto.setReplierResponsible(getReplierResponsible().getDto());
    dto.setWorkScheduleDto(getWorkSchedule().getDto());
    return dto;
  }

  public WorkSchedule getWorkSchedule() {
    return workSchedule;
  }
}
