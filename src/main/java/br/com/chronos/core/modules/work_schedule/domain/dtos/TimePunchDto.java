package br.com.chronos.core.modules.work_schedule.domain.dtos;

import java.time.LocalTime;

public class TimePunchDto {
  public String id;
  public LocalTime firstClockIn;
  public LocalTime firstClockOut;
  public LocalTime secondClockIn;
  public LocalTime secondClockOut;

  public TimePunchDto setId(String id) {
    this.id = id;
    return this;
  }

  public TimePunchDto setFirstClockIn(LocalTime firstClockIn) {
    this.firstClockIn = firstClockIn;
    return this;
  }

  public TimePunchDto setFirstClockOut(LocalTime firstClockOut) {
    this.firstClockOut = firstClockOut;
    return this;
  }

  public TimePunchDto setSecondClockIn(LocalTime secondClockIn) {
    this.secondClockOut = secondClockIn;
    return this;
  }

  public TimePunchDto setSecondClockOut(LocalTime secondClockOut) {
    this.secondClockOut = secondClockOut;
    return this;
  }

}
