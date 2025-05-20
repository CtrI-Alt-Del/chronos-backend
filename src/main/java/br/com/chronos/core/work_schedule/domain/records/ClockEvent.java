package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.records.PlusIntegerNumber;

public record ClockEvent(PlusIntegerNumber ClockIns, PlusIntegerNumber ClockOuts) {
  public static ClockEvent create() {
    return new ClockEvent(PlusIntegerNumber.create(0), PlusIntegerNumber.create(0));
  }

  public ClockEvent incrementClockIns(int value) {
    return new ClockEvent(ClockIns.plus(value), ClockOuts);
  }

  public ClockEvent incrementClockOuts(int value) {
    return new ClockEvent(ClockIns, ClockOuts.plus(value));

  }

}
