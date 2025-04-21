package br.com.chronos.core.work_schedule.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Logical;
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
  private Time hourBankCredit;
  private Time hourBankDebit;
  private static final Time LUNCH_TIME_SCHEDULE = Time.create(1, 0);

  public WorkdayLog(WorkdayLogDto dto) {
    super(dto.id);
    date = (dto.date != null) ? Date.create(dto.date) : Date.createFromNow();
    timePunch = new TimePunch(dto.timePunch);
    status = WorkdayStatus.create(dto.status);
    workloadSchedule = Workload.create(dto.workloadSchedule);
    hourBankCredit = (dto.hourBankCredit != null)
        ? Time.create(dto.hourBankCredit)
        : Time.createAsZero();
    hourBankDebit = (dto.hourBankDebit != null)
        ? Time.create(dto.hourBankDebit)
        : Time.createAsZero();
    responsible = new ResponsibleAggregate(dto.responsible);
  }

  public Time getOvertime() {
    var totalTime = timePunch.getTotalTime();
    var workloadScheduleTime = Time.create(workloadSchedule.value(), 0);
    var lunchTime = timePunch.getLunchTime();
    var totalOvertime = Time.createAsZero();

    if (totalTime.isGreaterThan(workloadScheduleTime).isTrue()) {
      var overtime = totalTime.minus(workloadScheduleTime);
      if (overtime.hasMoreHoursThan(2).isTrue()) {
        return Time.create(2, 0);
      }
      totalOvertime = totalOvertime.plus(overtime);
    }

    if (lunchTime.isLessThan(LUNCH_TIME_SCHEDULE).isTrue()) {
      var overtime = LUNCH_TIME_SCHEDULE.getDifferenceFrom(lunchTime);
      totalOvertime = totalOvertime.plus(overtime);
    }

    return totalOvertime;
  }

  public Time getUndertime() {
    var totalTime = timePunch.getTotalTime();
    var workloadScheduleTime = Time.create(workloadSchedule.value(), 0);

    if (totalTime.isLessThan(workloadScheduleTime).isTrue()) {
      return workloadScheduleTime.minus(totalTime);
    }

    return Time.createAsZero();
  }

  public Time getLatetime() {
    var lunchTime = timePunch.getLunchTime();
    var latetimeGrace = Time.create(0, 10);
    var lunchTimeSchedule = LUNCH_TIME_SCHEDULE.plus(latetimeGrace);

    if (lunchTime.isGreaterThan(lunchTimeSchedule).isTrue()) {
      return lunchTime.getDifferenceFrom(lunchTimeSchedule);
    }

    return Time.createAsZero();
  }

  public Logical verifyAbsense() {
    if (status.isNormalDay().and(timePunch.isEmpty()).isTrue()) {
      status = WorkdayStatus.createAsAbsence();
    }
    return status.isAbsence();
  }

  public Date getDate() {
    return date;
  }

  public void updateHourBank(Time time, Logical isCreditOperation) {
    if (isCreditOperation.isTrue()) {
      hourBankCredit = hourBankCredit.plus(time);
      return;
    }

    hourBankDebit = hourBankDebit.plus(time);
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

  public Time getHourBankCredit() {
    return hourBankCredit;
  }

  public Time getHourBankDebit() {
    return hourBankDebit;
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
        .setHourBankCredit(getHourBankCredit().value())
        .setHourBankDebit(getHourBankDebit().value())
        .setStatus(getStatus().toString().toLowerCase())
        .setResponsible(getResponsible().getDto());
  }

}
