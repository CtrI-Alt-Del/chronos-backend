package br.com.chronos.core.modules.solicitation.domain.dtos;

import java.time.LocalTime;

public class TimePunchLogAdjustmentSolicitationDto extends SolicitationDto {
  public LocalTime time;
  public String period;

  public TimePunchLogAdjustmentSolicitationDto setTime(LocalTime time) {
    this.time = time;
    return this;
  }

  public TimePunchLogAdjustmentSolicitationDto setPeriod(String period) {
    this.period = period;
    return this;
  }
}
