package br.com.chronos.core.modules.work_schedule.domain.dtos;

import java.time.LocalDate;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;

public class WorkdayLogDto {
  public String id;
  public LocalDate date;
  public TimePunchDto timePunchSchedule;
  public TimePunchDto timePunchLog;
  public String status;
  public WorkdayLogDtoResponsible responsible;

  public static class WorkdayLogDtoResponsible {
    public String id;
    public ResponsibleDto dto;

    public WorkdayLogDtoResponsible setId(String id) {
      this.id = id;
      return this;
    }

    public WorkdayLogDtoResponsible setDto(ResponsibleDto dto) {
      this.dto = dto;
      return this;
    }
  }

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

  public WorkdayLogDto setResponsible(String id) {
    this.responsible = new WorkdayLogDtoResponsible()
        .setId(id);
    return this;
  }

  public WorkdayLogDto setResponsible(String id, ResponsibleDto dto) {
    this.responsible = new WorkdayLogDtoResponsible()
        .setId(id)
        .setDto(dto);
    return this;
  }
}
