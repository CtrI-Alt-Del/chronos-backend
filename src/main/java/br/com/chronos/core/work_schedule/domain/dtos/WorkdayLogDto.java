package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;

public class WorkdayLogDto {
  public String id;
  public LocalDate date;
  public TimePunchDto timePunch;
  public String status;
  public byte workloadSchedule;
  public LocalTime hourBankCredit;
  public LocalTime hourBankDebit;
  public ResponsibleAggregateDto responsible;

  public WorkdayLogDto setId(String id) {
    this.id = id;
    return this;
  }

  public WorkdayLogDto setDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public WorkdayLogDto setTimePunch(TimePunchDto timePunch) {
    this.timePunch = timePunch;
    return this;
  }

  public WorkdayLogDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public WorkdayLogDto setWorkloadSchedule(byte workload) {
    this.workloadSchedule = workload;
    return this;
  }

  public WorkdayLogDto setHourBankCredit(LocalTime hourBankCredit) {
    this.hourBankCredit = hourBankCredit;
    return this;
  }

  public WorkdayLogDto setHourBankDebit(LocalTime hourBankDebit) {
    this.hourBankDebit = hourBankDebit;
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
