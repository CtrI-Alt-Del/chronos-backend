package br.com.chronos.server.api.controllers.work_schedule.week_schedules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.UpdateWeekScheduleUseCase;

@WeekSchedulesController
public class UpdateWeekScheduleController {
  @Autowired
  private WeekdaySchedulesRepository weekdaySchedulesRepository;

  @PutMapping("/{collaboratorId}")
  public ResponseEntity<TimePunchDto> handle(
      @PathVariable String collaboratorId,
      @RequestBody List<WeekdayScheduleDto> body) {
    var useCase = new UpdateWeekScheduleUseCase(weekdaySchedulesRepository);
    useCase.execute(collaboratorId, body);
    return ResponseEntity.noContent().build();
  }
}
