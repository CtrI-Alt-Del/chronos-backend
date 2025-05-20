package br.com.chronos.core.work_schedule.domain.dtos;

import java.util.List;

import br.com.chronos.core.work_schedule.domain.records.ClockEvent;

public class DailyTimePunchsReportDto {
  public List<ClockEventDto> clockEvents;

  public DailyTimePunchsReportDto setClockEvents(List<ClockEventDto> clockEvents) {
    this.clockEvents = clockEvents;
    return this;
  }

  public static DailyTimePunchsReportDto createFromValues(List<ClockEvent> clockEvents) {
    var clockEventsDto = clockEvents.stream()
        .map(clockEvent -> new ClockEventDto()
            .setClockIns(clockEvent.ClockIns().value())
            .setClockOuts(clockEvent.ClockOuts().value())).toList();
    return new DailyTimePunchsReportDto()
        .setClockEvents(clockEventsDto);
  }
}
