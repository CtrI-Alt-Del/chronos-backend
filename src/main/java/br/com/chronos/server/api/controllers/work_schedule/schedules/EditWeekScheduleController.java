package br.com.chronos.server.api.controllers.work_schedule.schedules;

import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.EditWeekScheduleUseCase;
import br.com.chronos.server.api.controllers.work_schedule.time_punches.TimePunchesController;

@TimePunchesController
public class EditWeekScheduleController {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @PutMapping("/{workScheduleId}/week-schedule")
  public ResponseEntity<TimePunchDto> handle(
      @PathVariable String workScheduleId,
      @RequestBody List<WeekdayScheduleDto> body) {
    var useCase = new EditWeekScheduleUseCase(workSchedulesRepository);
    useCase.execute(workScheduleId, body);
    return ResponseEntity.noContent().build();
  }
}
