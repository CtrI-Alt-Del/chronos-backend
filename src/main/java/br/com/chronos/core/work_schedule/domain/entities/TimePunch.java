package br.com.chronos.core.work_schedule.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.exceptions.ValidationException;
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
    super();
    firstClockIn = Time.create(dto.firstClockIn);
    firstClockOut = Time.create(dto.firstClockOut);
    secondClockIn = Time.create(dto.secondClockIn);
    secondClockOut = Time.create(dto.secondClockOut);
  }

  public void punch(Time time) {
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
    validate();
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
    validate();
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

  public Logical isEmpty() {
    return firstClockIn.isNull()
        .and(firstClockOut.isNull())
        .and(secondClockIn.isNull())
        .and(secondClockOut.isNull());
  }

  public void validate() {
    if (firstClockIn.isNotNull().and(firstClockOut.isNotNull()).isTrue() &&
        firstClockIn.isGreaterThan(firstClockOut).isTrue()) {
      throw new ValidationException("primeira entrada", "não pode ser maior que a primeira saída");
    }
    if (firstClockIn.isNotNull().and(secondClockIn.isNotNull()).isTrue() &&
        firstClockIn.isGreaterThan(secondClockIn).isTrue()) {
      throw new ValidationException("primeira entrada", "não pode ser maior que a segunda entrada");
    }
    if (firstClockIn.isNotNull().and(secondClockOut.isNotNull()).isTrue() &&
        firstClockIn.isGreaterThan(secondClockOut).isTrue()) {
      throw new ValidationException("primeira entrada", "não pode ser maior que a segunda saída");
    }
    if (firstClockOut.isNotNull().and(secondClockIn.isNotNull()).isTrue() &&
        firstClockOut.isGreaterThan(secondClockIn).isTrue()) {
      throw new ValidationException("primeira saída", "não pode ser maior que a segunda entrada");
    }
    if (firstClockOut.isNotNull().and(secondClockOut.isNotNull()).isTrue() &&
        firstClockOut.isGreaterThan(secondClockOut).isTrue()) {
      throw new ValidationException("primeira saída", "não pode ser maior que a segunda saída");
    }
    if (secondClockIn.isNotNull().and(secondClockOut.isNotNull()).isTrue() &&
        secondClockIn.isGreaterThan(secondClockOut).isTrue()) {
      throw new ValidationException("segunda entrada", "não pode ser maior que a segunda saída");
    }
  }

  public Time getTotalTime() {
    if (firstClockIn.isNull().or(firstClockOut.isNull()).isTrue()) {
      return Time.createAsZero();
    }

    var firstBlockTime = firstClockOut.getDifferenceFrom(firstClockIn);

    if (secondClockIn.isNull().or(secondClockOut.isNull()).isTrue()) {
      return firstBlockTime;
    }

    var secondBlockTime = secondClockOut.getDifferenceFrom(secondClockIn);
    return firstBlockTime.plus(secondBlockTime);
  }

  public Time getLunchTime() {
    if (firstClockOut.isNull().or(secondClockIn.isNull()).isTrue()) {
      return Time.createAsZero();
    }

    return secondClockIn.getDifferenceFrom(firstClockOut);
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
        .setFirstClockIn(getFirstClockIn().value())
        .setFirstClockOut(getFirstClockOut().value())
        .setSecondClockIn(getSecondClockIn().value())
        .setSecondClockOut(getSecondClockOut().value());
  }
}
