package br.com.chronos.server.api.controllers.work_schedule.day_off_schedules;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import br.com.chronos.core.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.GetDayOffScheduleUseCase;

@DayOffSchedulesController
public class GetDayOffScheduleController {
  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @GetMapping("/{collaboratorId}")
  public ResponseEntity<DayOffScheduleDto> handle(@PathVariable String collaboratorId) {
    var useCase = new GetDayOffScheduleUseCase(dayOffSchedulesRepository);
    var dayOffSchedule = useCase.execute(collaboratorId);
    return ResponseEntity.ok(dayOffSchedule);
  }
}
