package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeCardRowDto {
  public LocalDate date;
  public LocalTime overtime;
  public LocalTime undertime;
  public LocalTime latetime;
  public LocalTime workedTime;
  public LocalTime hourBankCredit;
  public LocalTime hourBankDebit;
  public TimePunchDto timePunch;
  public byte workload;
  public String workdayStatus;

  public TimeCardRowDto setDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public TimeCardRowDto setTimePunch(TimePunchDto timePunch) {
    this.timePunch = timePunch;
    return this;
  }

  public TimeCardRowDto setOvertime(LocalTime overtime) {
    this.overtime = overtime;
    return this;
  }

  public TimeCardRowDto setUndertime(LocalTime undertime) {
    this.undertime = undertime;
    return this;
  }

  public TimeCardRowDto setLatetime(LocalTime latetime) {
    this.latetime = latetime;
    return this;
  }

  public TimeCardRowDto setWorkedTime(LocalTime workedTime) {
    this.workedTime = workedTime;
    return this;
  }

  public TimeCardRowDto setHourBankCredit(LocalTime hourBankCredit) {
    this.hourBankCredit = hourBankCredit;
    return this;
  }

  public TimeCardRowDto setHourBankDebit(LocalTime hourBankDebit) {
    this.hourBankDebit = hourBankDebit;
    return this;
  }

  public TimeCardRowDto setWorkdayStatus(String workdayStatus) {
    this.workdayStatus = workdayStatus;
    return this;
  }

  public TimeCardRowDto setWorkload(byte workload) {
    this.workload = workload;
    return this;
  }
}
