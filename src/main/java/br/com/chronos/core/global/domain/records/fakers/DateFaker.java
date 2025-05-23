package br.com.chronos.core.global.domain.records.fakers;

import java.time.LocalDate;
import java.util.Random;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Date;

public class DateFaker {
  private static Random random = new Random();

  public static Date fake() {
    return Date.create(fakeDto());
  }

  public static LocalDate fakeDto() {
    int year = 2000 + random.nextInt(50);
    int month = 1 + random.nextInt(12);
    int day = 1 + random.nextInt(28);
    return LocalDate.of(year, month, day);
  }

  public static Array<LocalDate> fakeManyDtos(int count) {
    Array<LocalDate> fakeWeekScheduleDtos = Array.createAsEmpty();
    for (var index = 0; index < count; index++) {
      fakeWeekScheduleDtos.add(fakeDto());
    }
    return fakeWeekScheduleDtos;
  }
}
