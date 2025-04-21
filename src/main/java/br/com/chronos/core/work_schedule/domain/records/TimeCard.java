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
      Time overtime,
      Time undertime,
      Time latetime,
      Time workedTime,
      Time hourBankCredit,
      Time hourBankDebit) {

    public TimeCardRowDto getDto() {
      return new TimeCardRowDto()
          .setDate(date.value())
          .setTimePunch(timePunch.getDto())
          .setOvertime(overtime.value())
          .setUndertime(undertime.value())
          .setLatetime(latetime.value())
          .setWorkedTime(workedTime.value())
          .setHourBankCredit(hourBankCredit.value())
          .setHourBankDebit(hourBankDebit.value());
    }
  }

  public static TimeCard create(Array<WorkdayLog> workdayLogs) {
    var rows = workdayLogs.map(workdayLog -> {
      return new Row(
          workdayLog.getDate(),
          workdayLog.getTimePunch(),
          workdayLog.getOvertime(),
          workdayLog.getUndertime(),
          workdayLog.getLatetime(),
          workdayLog.getWorkedTime(),
          workdayLog.getHourBankCredit(),
          workdayLog.getHourBankDebit());
    });

    return new TimeCard(rows);
  }
}
