package br.com.chronos.core.modules.solicitation.domain.dtos;

import java.time.LocalTime;

public class TimePunchLogAdjustmentDto extends SolicitationDto {
  LocalTime time;
  String period;

  public TimePunchLogAdjustmentDto setTime(LocalTime time) {
    this.time = time;
    return this;
  }

  public TimePunchLogAdjustmentDto setPeriod(String period) {
    this.period = period;
    return this;
  }
}
