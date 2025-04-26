package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.dtos.WorkTimeLogDto;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;

public record WorkTimeLog(Time workdayTime, Time workMonthTime) {
  public static WorkTimeLog create(Array<WorkdayLog> workdayLogs) {
    var workdayTime = Time.createAsZero();
    var workMonthTime = Time.createAsZero();
    var today = Date.createFromNow();

    for (var workdayLog : workdayLogs.list()) {
      workMonthTime = workMonthTime.plus(workdayLog.getTimePunch().getTotalTime());
      if (workdayLog.getDate().isEqual(today).isTrue()) {
        workdayTime = workdayTime.plus(workdayLog.getTimePunch().getTotalTime());
      }
    }
    return new WorkTimeLog(workdayTime, workMonthTime);
  }

  public WorkTimeLogDto getDto() {
    return new WorkTimeLogDto()
        .setWorkdayTime(workdayTime.value())
        .setWorkMonthTime(workMonthTime.value());
  }
}
