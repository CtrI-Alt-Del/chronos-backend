package br.com.chronos.core.portal.domain.entities;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.domain.records.SolicitationType;

public class WorkleaveSolicitation extends Solicitation {
  private Date startedAt;
  private Date endedAt;
  private Justification justification;
  private Boolean isVacation;

  public WorkleaveSolicitation(WorkLeaveSolicitationDto dto) {
    super(dto);
    var dateRange = DateRange.create(dto.startedAt, dto.endedAt);
    this.startedAt = dateRange.startDate();
    this.endedAt = dateRange.endDate();
    this.isVacation = dto.isVacation;
    this.type = SolicitationType.createAsWorkLeave();
    this.justification = dto.justification != null ? new Justification(dto.justification) : null;
  }

  public void becomeVacation() {
    this.isVacation = true;
  }

  public Date getStartedAt() {
    return startedAt;
  }

  public Date getEndedAt() {
    return endedAt;
  }

  public Justification getJustification() {
    return justification;
  }

  public Boolean isVacation() {
    return isVacation;
  }

  public WorkLeaveSolicitationDto getDto() {
    var solicitationDto = super.getDto();
    WorkLeaveSolicitationDto dto = new WorkLeaveSolicitationDto();
    dto
        .setId(solicitationDto.id)
        .setDescription(solicitationDto.description)
        .setDate(solicitationDto.date)
        .setStatus(solicitationDto.status)
        .setFeedbackMessage(solicitationDto.feedbackMessage)
        .setSenderResponsible(solicitationDto.senderResponsible)
        .setReplierResponsible(solicitationDto.replierResponsible)
        .setStartedAt(getStartedAt().value())
        .setEndedAt(getEndedAt().value())
        .setIsVacation(isVacation())
        .setJustification(getJustification() != null ? getJustification().getDto() : null)
        .setType(getType().toString());
    return dto;

  }

}
