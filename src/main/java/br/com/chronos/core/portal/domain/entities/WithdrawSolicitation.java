package br.com.chronos.core.portal.domain.entities;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.WithdrawSolicitationDto;
import br.com.chronos.core.portal.domain.records.SolicitationType;

public final class WithdrawSolicitation extends Solicitation {
  private Array<Date> withdrawalDays;
  private Justification justification;

  public WithdrawSolicitation(WithdrawSolicitationDto dto) {
    super(dto);
    Array.createFrom(dto.withdrawalDays, Date::create);
    justification = dto.justification != null ? new Justification(dto.justification) : null;
    this.type = SolicitationType.createAsWithdraw();
  }

  public Array<Date> getWithdrawalDays() {
    return withdrawalDays;
  }

  public Justification getJustification() {
    return justification;
  }

  public WithdrawSolicitationDto getDto() {
    var solicitationDto = super.getDto();
    WithdrawSolicitationDto dto = new WithdrawSolicitationDto();
    dto
        .setId(solicitationDto.id)
        .setDescription(solicitationDto.description)
        .setDate(solicitationDto.date)
        .setStatus(solicitationDto.status)
        .setFeedbackMessage(solicitationDto.feedbackMessage)
        .setSenderResponsible(solicitationDto.senderResponsible)
        .setReplierResponsible(solicitationDto.replierResponsible)
        .setWithdrawalDays(getWithdrawalDays().map(date -> date.value()).list())
        .setJustification(getJustification() != null ? getJustification().getDto() : null)
        .setType(getType().toString());
    return dto;
  }
}
