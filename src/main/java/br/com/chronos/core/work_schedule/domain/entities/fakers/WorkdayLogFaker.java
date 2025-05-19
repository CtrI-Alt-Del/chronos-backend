package br.com.chronos.core.work_schedule.domain.entities.fakers;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.records.WorkdayStatus;

public class WorkdayLogFaker {
  public static WorkdayLog fake(String collaboratorId) {
    return new WorkdayLog(fakeDto(collaboratorId));
  }

  public static WorkdayLogDto fakeDto(String collaboratorId) {
    return new WorkdayLogDto()
        .setDate(Date.createFromNow().value())
        .setStatus(WorkdayStatus.WorkdayStatusName.NORMAL_DAY.name())
        .setWorkloadSchedule((byte) 8)
        .setTimePunch(TimePunchFaker.fakeDto())
        .setResponsible(new ResponsibleAggregateDto().setId(collaboratorId));
  }

}