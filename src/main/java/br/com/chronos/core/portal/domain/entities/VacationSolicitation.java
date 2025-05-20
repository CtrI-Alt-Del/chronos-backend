package br.com.chronos.core.portal.domain.entities;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.VacationSolicitationDto;
import br.com.chronos.core.portal.domain.records.SolicitationType;

public final class VacationSolicitation extends Solicitation {
  private Array<Date> vacationDays;

  public VacationSolicitation(VacationSolicitationDto dto) {
    super(dto);
    this.vacationDays = Array.createFrom(dto.vacationDays, Date::create);
    this.type = SolicitationType.createAsVacation();
  }

  public Array<Date> getVacationDays() {
    return vacationDays;
  }

  public LocalDate getStartDate() {
    return this.vacationDays.firstItem().value();
  }

  public LocalDate getEndDate() {
    return this.vacationDays.lastItem().value();
  }

  public VacationSolicitationDto getDto() {
    var solicitationDto = super.getDto();
    VacationSolicitationDto dto = new VacationSolicitationDto();
    dto
        .setId(solicitationDto.id)
        .setDescription(solicitationDto.description)
        .setDate(solicitationDto.date)
        .setStatus(solicitationDto.status)
        .setFeedbackMessage(solicitationDto.feedbackMessage)
        .setSenderResponsible(solicitationDto.senderResponsible)
        .setReplierResponsible(solicitationDto.replierResponsible)
        .setVacationDays(getVacationDays().map(date -> date.value()).list())
        .setType(getType().toString());
    return dto;
  }
}
