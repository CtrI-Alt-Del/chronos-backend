package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.dtos.TimeCardRowDto;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;

public record TimeCard(Array<Row> rows) {
  public static record Row(
      Date date,
      TimePunch timePunch,
      Workload workload,
      Time overtime,
      Time undertime,
      Time latetime,
      Time workedTime,
      Time hourBankCredit,
      Time hourBankDebit,
      WorkdayStatus workdayStatus) {

    public TimeCardRowDto getDto() {
      return new TimeCardRowDto()
          .setDate(date.value())
          .setWorkload(workload.value())
          .setTimePunch(timePunch.getDto())
          .setOvertime(overtime.value())
          .setUndertime(undertime.value())
          .setLatetime(latetime.value())
          .setWorkedTime(workedTime.value())
          .setHourBankCredit(hourBankCredit.value())
          .setHourBankDebit(hourBankDebit.value())
          .setWorkdayStatus(workdayStatus.toString());
    }
  }

  public static TimeCard create(Array<WorkdayLog> workdayLogs) {
    var rows = workdayLogs.map(workdayLog -> {
      // System.out.println(workdayLog.getTimePunch());
      return new Row(
          workdayLog.getDate(),
          workdayLog.getTimePunch(),
          workdayLog.getWorkloadSchedule(),
          workdayLog.getOvertime(),
          workdayLog.getUndertime(),
          workdayLog.getLatetime(),
          workdayLog.getWorkedTime(),
          workdayLog.getHourBankCredit(),
          workdayLog.getHourBankDebit(),
          workdayLog.getStatus());
    });

    return new TimeCard(rows);
  }
}
