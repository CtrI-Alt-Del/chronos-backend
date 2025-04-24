package br.com.chronos.core.work_schedule.domain.dtos;

import java.time.LocalTime;

public class TimePunchDto {
  public LocalTime firstClockIn;
  public LocalTime firstClockOut;
  public LocalTime secondClockIn;
  public LocalTime secondClockOut;

  public TimePunchDto setFirstClockIn(LocalTime firstClockIn) {
    this.firstClockIn = firstClockIn;
    return this;
  }

  public TimePunchDto setFirstClockOut(LocalTime firstClockOut) {
    this.firstClockOut = firstClockOut;
    return this;
  }

  public TimePunchDto setSecondClockIn(LocalTime secondClockIn) {
    this.secondClockIn = secondClockIn;
    return this;
  }

  public TimePunchDto setSecondClockOut(LocalTime secondClockOut) {
    this.secondClockOut = secondClockOut;
    return this;
  }

}
