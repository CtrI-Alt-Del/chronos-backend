package br.com.chronos.core.solicitation.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class ExcuseAbsenceSolicitationDto extends SolicitationDto{
  public LocalDate excuseAbsenceDate;
  public JustificationDto justification;

  public ExcuseAbsenceSolicitationDto setJustification(JustificationDto justificationDto) {
    this.justification = justificationDto;
    return this;
  }

  public ExcuseAbsenceSolicitationDto setExcuseAbsenceDate(LocalDate dayOff) {
    this.excuseAbsenceDate = dayOff;
    return this;
  }
  public ExcuseAbsenceSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }
  public ExcuseAbsenceSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }
  public ExcuseAbsenceSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }
  public ExcuseAbsenceSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }
  public ExcuseAbsenceSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }
  public ExcuseAbsenceSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }
  public ExcuseAbsenceSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }
}
