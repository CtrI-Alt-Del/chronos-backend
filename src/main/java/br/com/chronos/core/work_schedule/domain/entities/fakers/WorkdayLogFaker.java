package br.com.chronos.core.work_schedule.domain.entities.fakers;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.records.WorkdayStatus;

public class WorkdayLogFaker {
  public static WorkdayLog fakePresence(String collaboratorId) {
    return new WorkdayLog(fakePresenceDto(collaboratorId));
  }

  public static WorkdayLog fakeAbsence(String collaboratorId) {
    return new WorkdayLog(fakeAbsenceDto(collaboratorId));
  }

  public static WorkdayLogDto fakePresenceDto(String collaboratorId) {
    return new WorkdayLogDto()
        .setDate(Date.createFromNow().value())
        .setStatus(WorkdayStatus.WorkdayStatusName.NORMAL_DAY.name())
        .setWorkloadSchedule((byte) 8)
        .setTimePunch(TimePunchFaker.fakeDto())
        .setResponsible(new ResponsibleAggregateDto().setId(collaboratorId));
  }

  public static WorkdayLogDto fakeAbsenceDto(String collaboratorId) {
    return new WorkdayLogDto()
        .setDate(Date.createFromNow().minusDays(1).value())
        .setStatus(WorkdayStatus.WorkdayStatusName.ABSENCE.name())
        .setWorkloadSchedule((byte) 8)
        .setTimePunch(new TimePunchDto())
        .setResponsible(new ResponsibleAggregateDto().setId(collaboratorId));
  }
}