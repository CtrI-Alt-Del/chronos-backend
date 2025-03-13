package br.com.chronos.core.modules.work_schedule.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Time;
import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.records.TimePunchPeriod;

public final class TimePunch extends Entity {
  private Time firstClockIn;
  private Time firstClockOut;
  private Time secondClockIn;
  private Time secondClockOut;

  public TimePunch(TimePunchDto dto) {
    super(dto.id);
    firstClockIn = Time.create(dto.firstClockIn);
    firstClockOut = Time.create(dto.firstClockOut);
    secondClockIn = Time.create(dto.secondClockIn);
    secondClockOut = Time.create(dto.secondClockOut);
  }

  public void punch(Time time) {
    if (firstClockIn == null) {
      firstClockIn = time;
    } else if (firstClockOut == null) {
      firstClockOut = time;
    } else if (secondClockIn == null) {
      secondClockIn = time;
    } else {
      secondClockOut = time;
    }
  }

  public void adjust(Time time, TimePunchPeriod period) {
    switch (period.name()) {
      case TimePunchPeriod.PeriodName.FIRST_CLOCK_IN:
        firstClockIn = time;
        break;
      case TimePunchPeriod.PeriodName.FIRST_CLOCK_OUT:
        firstClockIn = time;
        break;
      case TimePunchPeriod.PeriodName.SECOND_CLOCK_IN:
        firstClockIn = time;
        break;
      case TimePunchPeriod.PeriodName.SECOND_CLOCK_OUT:
        firstClockIn = time;
        break;
    }
  }

  public void replaceWith(TimePunch timePunch) {
    firstClockIn = timePunch.getFirstClockIn();
    firstClockOut = timePunch.getFirstClockOut();
    secondClockIn = timePunch.getSecondClockIn();
    secondClockOut = timePunch.getSecondClockOut();
  }

  public Time getFirstClockIn() {
    return firstClockIn;
  }

  public Time getFirstClockOut() {
    return firstClockOut;
  }

  public Time getSecondClockIn() {
    return secondClockIn;
  }

  public Time getSecondClockOut() {
    return secondClockOut;
  }

  public TimePunchDto getDto() {
    return new TimePunchDto()
        .setId(getId().toString())
        .setFirstClockIn(getFirstClockIn().value())
        .setFirstClockOut(getFirstClockOut().value())
        .setSecondClockIn(getSecondClockIn().value())
        .setSecondClockOut(getSecondClockOut().value());
  }

}
