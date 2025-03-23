package br.com.chronos.core.modules.work_schedule.domain.records.fakers;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.modules.work_schedule.domain.records.DaysOffSchedule;

public class DaysOffScheduleFaker {
  public static DaysOffSchedule fake(int workdaysCount, int daysOffCount) {
    return DaysOffSchedule.create(fakeDto(workdaysCount, daysOffCount));
  }

  public static List<LocalDate> fakeDto(int workdaysCount, int daysOffCount) {
    var daysOff = DaysOffSchedule.create(workdaysCount, daysOffCount).days();
    return daysOff.map((dayOff) -> dayOff.value()).list();
  }
}
