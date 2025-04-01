package br.com.chronos.server.api.controllers.work_schedule.week_schedules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.GetWeekScheduleUseCase;

@WeekSchedulesController
public class GetWeekScheduleController {
  @Autowired
  private WeekdaySchedulesRepository weekdaySchedulesRepository;

  @GetMapping("/{collaboratorId}")
  public ResponseEntity<List<WeekdayScheduleDto>> handle(
      @PathVariable String collaboratorId,
      @RequestBody List<WeekdayScheduleDto> body) {
    var useCase = new GetWeekScheduleUseCase(weekdaySchedulesRepository);
    var weekSchedule = useCase.execute(collaboratorId);
    return ResponseEntity.ok(weekSchedule);
  }
}
