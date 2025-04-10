package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class WorkdayLogDto {
  public String id;
  public LocalDate date;
  public TimePunchDto timePunchSchedule;
  public TimePunchDto timePunchLog;
  public String status;
  public byte workload;
  public ResponsibleAggregateDto responsible;

  public WorkdayLogDto setId(String id) {
    this.id = id;
    return this;
  }

  public WorkdayLogDto setDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public WorkdayLogDto setTimePunchSchedule(TimePunchDto timePunch) {
    this.timePunchSchedule = timePunch;
    return this;
  }

  public WorkdayLogDto setTimePunchLog(TimePunchDto timePunch) {
    this.timePunchLog = timePunch;
    return this;
  }

  public WorkdayLogDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public WorkdayLogDto setWorkload(byte workload) {
    this.workload = workload;
    return this;
  }

  public WorkdayLogDto setResponsible(ResponsibleAggregateDto dto) {
    this.responsible = dto;
    return this;
  }

  public WorkdayLogDto setResponsibleId(String id) {
    this.responsible = new ResponsibleAggregateDto().setId(id);
    return this;
  }
}
