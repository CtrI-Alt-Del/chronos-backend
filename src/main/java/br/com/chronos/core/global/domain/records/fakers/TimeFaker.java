package br.com.chronos.core.global.domain.records.fakers;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Time;

import java.util.Random;

public class TimeFaker {
  private static Random random = new Random();

  public static Time fake() {
    return Time.create(fakeDto());
  }

  public static LocalTime fakeDto() {
    var hour = random.nextInt(16);
    var minute = random.nextInt(60);
    var randomTime = LocalTime.of(hour, minute);
    return randomTime;
  }
}
