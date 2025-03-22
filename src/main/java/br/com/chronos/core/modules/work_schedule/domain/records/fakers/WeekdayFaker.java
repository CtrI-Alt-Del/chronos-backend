package br.com.chronos.core.modules.work_schedule.domain.records.fakers;

import java.util.Random;

import br.com.chronos.core.modules.work_schedule.domain.records.Weekday;
import br.com.chronos.core.modules.work_schedule.domain.records.Weekday.WeekdayName;

public class WeekdayFaker {
  private static Random random = new Random();

  public static Weekday fake() {
    return Weekday.create(fakeDto());
  }

  public static String fakeDto() {
    var weekdayName = WeekdayName.values()[random.nextInt(WeekdayName.values().length)];
    return weekdayName.toString();
  }
}
