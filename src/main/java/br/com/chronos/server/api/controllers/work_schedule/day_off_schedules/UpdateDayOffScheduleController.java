package br.com.chronos.server.api.controllers.work_schedule.day_off_schedules;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.UpdateDaysOffScheduleUseCase;

@DayOffSchedulesController
public class UpdateDayOffScheduleController {
  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @PutMapping("/{collaboratorId}")
  public ResponseEntity<Void> handle(
      @PathVariable String collaboratorId,
      @RequestBody DayOffScheduleDto body) {
    var useCase = new UpdateDaysOffScheduleUseCase(dayOffSchedulesRepository);
    useCase.execute(collaboratorId, body);
    return ResponseEntity.noContent().build();
  }
}
