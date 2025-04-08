package br.com.chronos.core.work_schedule.domain.entities.fakers;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.entities.fakers.ResponsibleFaker;
import br.com.chronos.core.global.domain.records.fakers.DateFaker;
import br.com.chronos.core.global.domain.records.fakers.IdFaker;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.work_schedule.domain.records.fakers.WorkdayStatusFaker;

public class WorkdayLogFaker {
  public static WorkdayLog fake() {
    return new WorkdayLog(fakeDto());
  }

  public static WorkdayLogDto fakeDto() {
    var fakeResponsibleDto = ResponsibleFaker.fakeDto();
    return new WorkdayLogDto()
        .setId(IdFaker.fakeDto())
        .setDate(DateFaker.fakeDto())
        .setTimePunch(TimePunchFaker.fakeDto())
        .setStatus(WorkdayStatusFaker.fakeDto())
        .setResponsible(new ResponsibleAggregateDto(fakeResponsibleDto));
  }

  public static Array<WorkdayLog> fakeMany(int count) {
    Array<WorkdayLog> fakeWordayLogs = Array.createAsEmpty();
    for (var index = 0; index < count; index++) {
      fakeWordayLogs.add(fake());
    }
    return fakeWordayLogs;
  }
}
