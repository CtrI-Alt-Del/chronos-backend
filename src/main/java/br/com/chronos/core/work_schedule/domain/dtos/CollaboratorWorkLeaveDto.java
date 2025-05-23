package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.portal.domain.dtos.JustificationDto;

public class CollaboratorWorkLeaveDto {
  public List<LocalDate> dates;
  public Boolean isVacation;
  public JustificationDto justification;
  public ResponsibleDto collaborator;

  public CollaboratorWorkLeaveDto setDates(List<LocalDate> dates) {
    this.dates = dates;
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
