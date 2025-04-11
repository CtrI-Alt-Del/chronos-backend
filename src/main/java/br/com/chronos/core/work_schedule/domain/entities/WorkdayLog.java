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
    var workloadScheduleTime = Time.create(workloadSchedule.value(), 0);

    if (totalTime.isGreaterThan(workloadScheduleTime).isTrue()) {
      var overtime = totalTime.minus(workloadScheduleTime);
      if (overtime.hasMoreHoursThan(2).isTrue()) {
        return Time.create(2, 0);
      }
      return overtime;
    }

    return Time.createAsZero();
  }

  public Time getUndertime() {
    var totalTime = timePunch.getTotalTime();
    var workloadScheduleTime = Time.create(workloadSchedule.value(), 0);

    if (totalTime.isLessThan(workloadScheduleTime).isTrue()) {
      return workloadScheduleTime.minus(totalTime);
    }

    System.out.println(Time.createAsZero());
    return Time.createAsZero();
  }

  public Time getLatetime() {
    var lunchTimeSchedule = Time.create(1, 10);
    var lunchTime = timePunch.getLunchTime();

    if (lunchTime.isGreaterThan(lunchTimeSchedule).isTrue()) {
      return lunchTime.getDifferenceFrom(lunchTimeSchedule);
    }

    return Time.createAsZero();
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
        .setWorkloadSchedule(getWorkloadSchedule().value())
        .setTimePunch(getTimePunch().getDto())
        .setStatus(getStatus().toString().toLowerCase())
        .setResponsible(getResponsible().getDto());
  }

}
