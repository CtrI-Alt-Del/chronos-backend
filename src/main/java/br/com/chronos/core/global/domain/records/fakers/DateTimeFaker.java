package br.com.chronos.core.global.domain.records.fakers;

import java.time.LocalDateTime;
import java.util.Random;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.DateTime;

public class DateTimeFaker {
  private static Random random = new Random();

  public static DateTime fake() {
    return DateTime.create(fakeDto());
  }

  public static LocalDateTime fakeDto() {
    int year = 2000 + random.nextInt(50);
    int month = 1 + random.nextInt(12);
    int day = 1 + random.nextInt(28);
    int hour = random.nextInt(24);
    int minute = random.nextInt(60);
    return LocalDateTime.of(year, month, day, hour, minute);
  }

  public static Array<LocalDateTime> fakeManyDtos(int count) {
    Array<LocalDateTime> fakeWeekScheduleDtos = Array.createAsEmpty();
    for (var index = 0; index < count; index++) {
      fakeWeekScheduleDtos.add(fakeDto());
    }
    return fakeWeekScheduleDtos;
  }
}
