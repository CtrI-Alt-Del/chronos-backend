package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.portal.domain.dtos.JustificationDto;

public class CollaboratorWorkLeaveDto {
  public LocalDate startedAt;
  public LocalDate endedAt;
  public Boolean isVacation;
  public JustificationDto justification;
  public ResponsibleDto collaborator;

  public CollaboratorWorkLeaveDto setStartedAt(LocalDate startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public CollaboratorWorkLeaveDto setEndedAt(LocalDate endedAt) {
    this.endedAt = endedAt;
    return this;
  }

  public CollaboratorWorkLeaveDto setIsVacation(Boolean isVacation) {
    this.isVacation = isVacation;
    return this;
  }

  public CollaboratorWorkLeaveDto setJustification(JustificationDto justification) {
    this.justification = justification;
    return this;
  }

  public CollaboratorWorkLeaveDto setCollaborator(ResponsibleDto collaborator) {
    this.collaborator = collaborator;
    return this;
  }
}
