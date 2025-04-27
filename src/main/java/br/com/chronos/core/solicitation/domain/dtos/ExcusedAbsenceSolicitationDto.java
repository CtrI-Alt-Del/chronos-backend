package br.com.chronos.core.solicitation.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class ExcusedAbsenceSolicitationDto extends SolicitationDto {
  public LocalDate absenceDate;
  public JustificationDto justification;

  public ExcusedAbsenceSolicitationDto setJustification(JustificationDto justificationDto) {
    this.justification = justificationDto;
    return this;
  }

  public ExcusedAbsenceSolicitationDto setAbsenceDate(LocalDate dayOff) {
    this.absenceDate = dayOff;
    return this;
  }

  public ExcusedAbsenceSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }

  public ExcusedAbsenceSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }

  public ExcusedAbsenceSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }

  public ExcusedAbsenceSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }

  public ExcusedAbsenceSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }

  public ExcusedAbsenceSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }

  public ExcusedAbsenceSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }
}
