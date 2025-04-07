package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalDate;

public class HolidayDto {
  public String id;
  public String name;
  public LocalDate date;

  public HolidayDto setId(String id) {
    this.id = id;
    return this;
  }

  public HolidayDto setName(String name) {
    this.name = name;
    return this;
  }

  public HolidayDto setDate(LocalDate date) {
    this.date = date;
    return this;
  }
}
