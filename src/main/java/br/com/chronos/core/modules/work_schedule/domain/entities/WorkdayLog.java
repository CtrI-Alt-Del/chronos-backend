package br.com.chronos.core.modules.work_schedule.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Time;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus;

public final class WorkdayLog extends Entity {
  private Date date;
  private TimePunch timePunchSchedule;
  private TimePunch timePunchLog;
  private WorkdayStatus status;

  public WorkdayLog(WorkdayLogDto dto) {
    super(dto.id);
    date = Date.create(dto.date);
    timePunchSchedule = new TimePunch(dto.timePunchSchedule);
    timePunchLog = (dto.timePunchLog != null) ? new TimePunch(dto.timePunchLog) : new TimePunch();
    status = WorkdayStatus.create(dto.status);
  }

  public Time getLatetime() {
    var firstTimeInDifference = timePunchSchedule.getFirstClockIn()
        .getDifferenceFrom(timePunchLog.getFirstClockIn());
    var secondTimeInDifference = timePunchSchedule.getFirstClockOut()
        .getDifferenceFrom(timePunchLog.getFirstClockOut());

    var totalLatetime = firstTimeInDifference.plus(secondTimeInDifference);
    return totalLatetime;
  }

  public void justify() {
    timePunchLog.replaceWith(timePunchSchedule);
  }

  public Date getDate() {
    return date;
  }

  public TimePunch getTimePunchSchedule() {
    return timePunchSchedule;
  }

  public TimePunch getTimePunchLog() {
    return timePunchLog;
  }

  public WorkdayStatus getStatus() {
    return status;
  }

  public WorkdayLogDto getDto() {
    return new WorkdayLogDto()
        .setId(getId().toString())
        .setDate(getDate().value())
        .setTimePunchSchedule(getTimePunchSchedule().getDto())
        .setTimePunchLog(getTimePunchLog().getDto());
  }

}
