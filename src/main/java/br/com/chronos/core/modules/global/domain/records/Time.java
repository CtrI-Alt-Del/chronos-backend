package br.com.chronos.core.modules.global.domain.records;

import java.time.Duration;
import java.time.LocalTime;

public record Time(LocalTime value) {
  public static Time create(LocalTime value) {
    return new Time(value);
  }

  public Time getDifferenceFrom(Time time) {
    var duration = Duration.between(value, time.value());
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

  public boolean hasMoreMinutesThan(int minutes) {
    var duration = Duration.between(LocalTime.MIDNIGHT, value);
    var minutesDuration = Duration.ofMinutes(10);
    return duration.compareTo(minutesDuration) > 0;
  }
}
