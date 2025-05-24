package br.com.chronos.core.portal.domain.records;

import br.com.chronos.core.global.domain.entities.Responsible;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.portal.domain.entities.Justification;
import br.com.chronos.core.work_schedule.domain.dtos.CollaboratorWorkLeaveDto;
import br.com.chronos.core.work_schedule.domain.records.WorkdayStatus;

public record CollaboratorWorkLeave(
    DateRange dateRange,
    Logical isVacation,
    Justification justification,
    Responsible collaborator) {
  public static CollaboratorWorkLeave create(CollaboratorWorkLeaveDto dto) {
    return new CollaboratorWorkLeave(
        DateRange.create(dto.startedAt, dto.endedAt, 30),
        Logical.create(dto.isVacation),
        new Justification(dto.justification),
        new Responsible(dto.collaborator));
  }

  public WorkdayStatus getWorkdayStatus() {
    if (isVacation.isTrue()) {
      return WorkdayStatus.createAsVacation();
    }
    return WorkdayStatus.createAsWithdraw();
  }

  public CollaboratorWorkLeaveDto toDto() {
    return new CollaboratorWorkLeaveDto()
        .setStartedAt(dateRange.startDate().value())
        .setEndedAt(dateRange.endDate().value())
        .setIsVacation(isVacation.value())
        .setJustification(justification.getDto())
        .setCollaborator(collaborator.getDto());
  }
}
