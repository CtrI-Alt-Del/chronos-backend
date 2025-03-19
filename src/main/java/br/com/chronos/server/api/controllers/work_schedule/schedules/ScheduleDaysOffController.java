package br.com.chronos.server.api.controllers.work_schedule.schedules;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.modules.work_schedule.use_cases.ScheduleDaysOffUseCase;

@WorkSchedulesController
public class ScheduleDaysOffController {
  @GetMapping("/days-off-schedule")
  public ResponseEntity<List<LocalDate>> handle(
      @RequestParam int workdaysCount,
      @RequestParam int daysOffCount) {
    var useCase = new ScheduleDaysOffUseCase();
    var daysOffSchedule = useCase.execute(workdaysCount, daysOffCount);
    return ResponseEntity.ok(daysOffSchedule);
  }
}
