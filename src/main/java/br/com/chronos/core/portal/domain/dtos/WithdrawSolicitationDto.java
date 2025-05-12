
package br.com.chronos.core.portal.domain.dtos;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class WithdrawSolicitationDto extends SolicitationDto {
  public List<LocalDate> withdrawalDays;
  public JustificationDto justification;

  public WithdrawSolicitationDto setWithdrawalDays(List<LocalDate> vacationDays) {
    this.withdrawalDays = vacationDays;
    return this;
  }

  public WithdrawSolicitationDto setJustification(JustificationDto justificationDto) {
    this.justification = justificationDto;
    return this;
  }

  public WithdrawSolicitationDto setId(String id) {
    super.setId(id);
    return this;
  }

  public WithdrawSolicitationDto setDescription(String description) {
    super.setDescription(description);
    return this;
  }

  public WithdrawSolicitationDto setDate(LocalDate date) {
    super.setDate(date);
    return this;
  }

  public WithdrawSolicitationDto setStatus(String status) {
    super.setStatus(status);
    return this;
  }

  public WithdrawSolicitationDto setFeedbackMessage(String feedbackMessage) {
    super.setFeedbackMessage(feedbackMessage);
    return this;
  }

  public WithdrawSolicitationDto setSenderResponsible(ResponsibleAggregateDto senderResponsible) {
    super.setSenderResponsible(senderResponsible);
    return this;
  }

  public WithdrawSolicitationDto setReplierResponsible(ResponsibleAggregateDto replierResponsible) {
    super.setReplierResponsible(replierResponsible);
    return this;
  }
}
