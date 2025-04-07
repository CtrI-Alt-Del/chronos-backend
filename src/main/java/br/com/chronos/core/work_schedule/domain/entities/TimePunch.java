package br.com.chronos.core.work_schedule.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.exceptions.TimePunchNotOpenException;
import br.com.chronos.core.work_schedule.domain.records.TimePunchPeriod;

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

  public TimePunch() {
    super();
    firstClockIn = null;
    firstClockOut = null;
    secondClockIn = null;
    secondClockOut = null;
  }

  public void punch(Time time) {
    System.out.println("isClosed(): " + isClosed());
    if (isClosed().isTrue()) {
      throw new TimePunchNotOpenException();
    }

    if (firstClockIn.isNull().isTrue()) {
      firstClockIn = time;
    } else if (firstClockOut.isNull().isTrue()) {
      firstClockOut = time;
    } else if (secondClockIn.isNull().isTrue()) {
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
        firstClockOut = time;
        break;
      case TimePunchPeriod.PeriodName.SECOND_CLOCK_IN:
        secondClockIn = time;
        break;
      case TimePunchPeriod.PeriodName.SECOND_CLOCK_OUT:
        secondClockOut = time;
        break;
    }
  }

  public void replaceWith(TimePunch timePunch) {
    firstClockIn = timePunch.getFirstClockIn();
    firstClockOut = timePunch.getFirstClockOut();
    secondClockIn = timePunch.getSecondClockIn();
    secondClockOut = timePunch.getSecondClockOut();
  }

  public Logical isClosed() {
    return firstClockIn.isNull()
        .andNot(firstClockOut.isNull())
        .andNot(secondClockIn.isNull())
        .andNot(secondClockOut.isNull());
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
