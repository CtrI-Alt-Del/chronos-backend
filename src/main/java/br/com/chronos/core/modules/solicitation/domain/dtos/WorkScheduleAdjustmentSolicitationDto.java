package br.com.chronos.core.modules.solicitation.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;

public class WorkScheduleAdjustmentSolicitationDto extends SolicitationDto {
  public String workScheduleId;
  public WorkScheduleAdjustmentSolicitationDto setWorkScheduleId(String workScheduleId){
    this.workScheduleId = workScheduleId;
    return this;
  }
  public WorkScheduleAdjustmentSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }

  public WorkScheduleAdjustmentSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }

  public WorkScheduleAdjustmentSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }

  public WorkScheduleAdjustmentSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }

  public WorkScheduleAdjustmentSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }

  public WorkScheduleAdjustmentSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }

  public WorkScheduleAdjustmentSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }
}
