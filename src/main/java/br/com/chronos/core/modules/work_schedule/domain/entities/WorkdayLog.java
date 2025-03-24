package br.com.chronos.core.modules.work_schedule.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.entities.Responsible;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Time;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus;

public final class WorkdayLog extends Entity {
  private Date date;
  private TimePunch timePunchSchedule;
  private TimePunch timePunchLog;
  private WorkdayStatus status;
  private WorkdayLogResponsible responsible;

  public static class WorkdayLogResponsible {
    private Id id;
    private Responsible entity;

    public WorkdayLogResponsible(String id, ResponsibleDto dto) {
      this.id = Id.create(id);
      this.entity = (dto != null) ? new Responsible(dto) : null;
    }

    public Id getId() {
      return id;
    }

    public Responsible getEntity() {
      return entity;
    }
  }

  public WorkdayLog(WorkdayLogDto dto) {
    super(dto.id);
    date = (dto.date != null) ? Date.create(dto.date) : Date.createFromNow();
    timePunchSchedule = new TimePunch(dto.timePunchSchedule);
    timePunchLog = (dto.timePunchLog != null) ? new TimePunch(dto.timePunchLog) : new TimePunch();
    status = WorkdayStatus.create(dto.status);
    responsible = new WorkdayLogResponsible(dto.responsible.id, dto.responsible.dto);
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

  public WorkdayLogResponsible getResponsible() {
    return responsible;
  }

  public WorkdayLogDto getDto() {
    var responsibleDto = (getResponsible().getEntity() != null) ? getResponsible().getEntity().getDto() : null;
    return new WorkdayLogDto()
        .setId(getId().toString())
        .setDate(getDate().value())
        .setTimePunchSchedule(getTimePunchSchedule().getDto())
        .setTimePunchLog(getTimePunchLog().getDto())
        .setStatus(getStatus().toString().toLowerCase())
        .setResponsible(
            getResponsible().getId().toString(), responsibleDto);
  }

}
