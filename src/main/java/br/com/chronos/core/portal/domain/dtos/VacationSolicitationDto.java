package br.com.chronos.core.portal.domain.dtos;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class VacationSolicitationDto extends SolicitationDto{
  public List<LocalDate> vacationDays;

  public VacationSolicitationDto setVacationDays(List<LocalDate> vacationDays) {
    this.vacationDays = vacationDays;
    return this;
  }
  public VacationSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }
  public VacationSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }
  public VacationSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }
  public VacationSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }
  public VacationSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }
  public VacationSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }
  public VacationSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }
}

