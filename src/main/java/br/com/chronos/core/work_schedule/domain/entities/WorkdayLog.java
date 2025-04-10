package br.com.chronos.core.work_schedule.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.domain.records.WorkdayStatus;
import br.com.chronos.core.work_schedule.domain.records.Workload;

public final class WorkdayLog extends Entity {
  private Date date;
  private TimePunch timePunch;
  private WorkdayStatus status;
  private Workload workloadSchedule;
  private ResponsibleAggregate responsible;
  private static final int LUNCH_MINUTES = 60;

  public WorkdayLog(WorkdayLogDto dto) {
    super(dto.id);
    date = (dto.date != null) ? Date.create(dto.date) : Date.createFromNow();
    timePunch = (dto.timePunch != null) ? new TimePunch(dto.timePunch) : new TimePunch();
    status = WorkdayStatus.create(dto.status);
    workloadSchedule = Workload.create(dto.workloadSchedule);
    responsible = new ResponsibleAggregate(dto.responsible);
  }

  public Time getOvertime() {
    var totalTime = timePunch.getTotalTime();
    var overtime = totalTime.getDifferenceFrom(workloadSchedule.toTime());
    return overtime;
  }

  public Time getLatetime() {
    var lunchTime = timePunch.getLunchTime();

    if (lunchTime.hasLessMinutesThan(LUNCH_MINUTES).isTrue()) {

    }

    return timePunch.getTotalTime();
  }

  public Date getDate() {
    return date;
  }

  public Workload getWorkloadSchedule() {
    return workloadSchedule;
  }

  public TimePunch getTimePunch() {
    return timePunch;
  }

  public WorkdayStatus getStatus() {
    return status;
  }

  public ResponsibleAggregate getResponsible() {
    return responsible;
  }

  public WorkdayLogDto getDto() {
    return new WorkdayLogDto()
        .setId(getId().toString())
        .setDate(getDate().value())
        .setWorkloadSchedule(getWorkloadSchedule().hours().value())
        .setTimePunch(getTimePunch().getDto())
        .setStatus(getStatus().toString().toLowerCase())
        .setResponsible(getResponsible().getDto());
  }

}
