package br.com.chronos.core.global.domain.records;

import java.time.Duration;
import java.time.LocalTime;

public record TimeInterval(Duration value) {
  public static TimeInterval create(Duration value) {
    return new TimeInterval(value);
  }

  public static TimeInterval create() {
    return new TimeInterval(Duration.ZERO);
  }

  public TimeInterval plus(Time time) {
    var duration = Duration.between(LocalTime.MIDNIGHT, time.value());
    return new TimeInterval(value.plus(duration));
  }

  public TimeInterval minus(Time time) {
    var duration = Duration.between(LocalTime.MIDNIGHT, time.value());
    return new TimeInterval(value.minus(duration));
  }

  public TimeInterval minus(TimeInterval timeInterval) {
    return new TimeInterval(value.minus(timeInterval.value()));
  }

  public Logical isGreaterThan(TimeInterval time) {
    return Logical.create(value.compareTo(time.value()) > 0);
  }

  public Logical isLessThan(TimeInterval time) {
    return Logical.create(value.compareTo(time.value()) < 0);
  }

  public String getDto() {
    var hours = value.toHours();
    var minutes = value.toMinutes() % 60;
    return String.format("%02d:%02d", hours, minutes);
  }
}
