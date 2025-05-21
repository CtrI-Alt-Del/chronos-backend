package br.com.chronos.core.portal.domain.entities;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.domain.records.SolicitationType;

public class WorkLeaveSolicitation extends Solicitation {
  private Date startedAt;
  private Date endedAt;
  private Justification justification;
  private Logical isVacation;

  public WorkLeaveSolicitation(WorkLeaveSolicitationDto dto) {
    super(dto);
    var dateRange = DateRange.create(dto.startedAt, dto.endedAt, 30);
    this.startedAt = dateRange.startDate();
    this.endedAt = dateRange.endDate();
    this.isVacation = dto.isVacation != null ? Logical.create(dto.isVacation) : Logical.createAsFalse();
    this.type = SolicitationType.createAsWorkLeave();
    this.justification = dto.justification != null ? new Justification(dto.justification) : null;
  }

  public void becomeVacation() {
    isVacation = isVacation.becomeTrue();
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

  public Logical getIsVacation() {
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
        .setIsVacation(getIsVacation().value())
        .setJustification(getJustification() != null ? getJustification().getDto() : null)
        .setType(getType().toString());
    return dto;

  }

}
