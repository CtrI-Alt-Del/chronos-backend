package br.com.chronos.core.work_schedule.domain.dtos;

public class ClockEventDto {
  public int clockIns;
  public int clockOuts;

  public ClockEventDto setClockIns(int clockIns) {
    this.clockIns = clockIns;
    return this;
  }
  public ClockEventDto setClockOuts(int clockOuts) {
    this.clockOuts = clockOuts;
    return this;
  }
}
