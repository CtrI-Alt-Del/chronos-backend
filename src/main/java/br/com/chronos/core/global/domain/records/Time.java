package br.com.chronos.core.global.domain.records;

import java.time.Duration;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record Time(LocalTime value) {
  public static Time create(LocalTime value) {
    if (value != null && !(value instanceof LocalTime)) {
      throw new ValidationException("Tempo", "deve ser um horário válido ou nulo");
    }

    return new Time(value);
  }

  public static Time create(LocalTime value, String key) {
    if (value != null && !(value instanceof LocalTime)) {
      throw new ValidationException(key, "deve ser um horário válido");
    }

    return new Time(value);
  }

  public static Time create(int hours, int minutes) {
    if (hours < 0 || hours > 23) {
      throw new ValidationException("Horas de tempo", "deve ser entre 0 e 23");
    }
    if (minutes < 0 || minutes > 59) {
      throw new ValidationException("Minutos de tempo", "deve ser entre 0 e 59");
    }
    return new Time(LocalTime.of(hours, minutes));
  }

  public static Time createAsZero() {
    return new Time(LocalTime.of(0, 0));
  }

  public Time getDifferenceFrom(Time time) {
    var duration = Duration.between(time.value(), value);
    var hours = duration.toHours();
    var minutes = duration.toMinutes() % 60;
    return new Time(LocalTime.of((int) hours, (int) minutes));
  }

  public Time plus(Time time) {
    var currentDuration = Duration.between(LocalTime.MIDNIGHT, value);
    var timeDuration = Duration.between(LocalTime.MIDNIGHT, time.value());
    var totalDuration = currentDuration.plus(timeDuration);
    LocalTime timeResult = LocalTime.ofNanoOfDay(totalDuration.toNanos());
    return new Time(timeResult);
  }

  public Time minus(Time time) {
    var currentDuration = Duration.between(LocalTime.MIDNIGHT, value);
    var timeDuration = Duration.between(LocalTime.MIDNIGHT, time.value());
    var totalDuration = currentDuration.minus(timeDuration);
    LocalTime timeResult = LocalTime.ofNanoOfDay(totalDuration.toNanos());
    return new Time(timeResult);
  }

  public Time plusHours(int hoursCount) {
    return new Time(value.plusHours(hoursCount));
  }

  public Time plusMinutes(int minutesCount) {
    return new Time(value.plusMinutes(minutesCount));
  }

  public Logical isZero() {
    return Logical.create(value.getHour() == 0 && value.getMinute() == 0);
  }

  public Logical isGreaterThan(Time time) {
    return Logical.create(value.compareTo(time.value()) > 0);
  }

  public Logical isLessThan(Time time) {
    return Logical.create(value.compareTo(time.value()) < 0);
  }

  public Logical hasMoreMinutesThan(int minutes) {
    var duration = Duration.between(LocalTime.MIDNIGHT, value);
    var minutesDuration = Duration.ofMinutes(minutes);
    return Logical.create(duration.compareTo(minutesDuration) > 0);
  }

  public Logical hasLessMinutesThan(int minutes) {
    var duration = Duration.between(LocalTime.MIDNIGHT, value);
    var minutesDuration = Duration.ofMinutes(minutes);
    return Logical.create(duration.compareTo(minutesDuration) < 0);
  }

  public Logical hasMoreHoursThan(int hours) {
    var duration = Duration.between(LocalTime.MIDNIGHT, value);
    var hoursDuration = Duration.ofHours(hours);
    return Logical.create(duration.compareTo(hoursDuration) > 0);
  }

  public Logical isNull() {
    return Logical.create(value == null);
  }
}
