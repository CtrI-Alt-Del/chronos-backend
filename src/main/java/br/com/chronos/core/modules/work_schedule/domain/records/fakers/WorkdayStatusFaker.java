package br.com.chronos.core.modules.work_schedule.domain.records.fakers;

import java.util.Random;

import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus;
import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus.WorkdayStatusName;

public class WorkdayStatusFaker {
  private static Random random = new Random();

  public static WorkdayStatus fake() {
    return WorkdayStatus.create(fakeDto());
  }

  public static String fakeDto() {
    var workdayStatusName = WorkdayStatusName
        .values()[random.nextInt(WorkdayStatusName.values().length)];
    return workdayStatusName.toString();
  }
}
