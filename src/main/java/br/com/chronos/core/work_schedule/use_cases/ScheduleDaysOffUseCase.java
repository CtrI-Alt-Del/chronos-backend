package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.work_schedule.domain.records.DaysOffSchedule;

public class ScheduleDaysOffUseCase {
  public List<LocalDate> execute(int workdaysCount, int daysOffCount) {
    var daysOffSchedule = DaysOffSchedule.create(workdaysCount, daysOffCount);
    return daysOffSchedule.days().map((dayOff) -> dayOff.value()).list();
  }
}
