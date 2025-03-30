package br.com.chronos.server.api.controllers.work_schedule.schedules;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.EditWeekScheduleUseCase;

@WorkSchedulesController
public class EditWeekScheduleController {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @PutMapping("/{workScheduleId}/week-schedule")
  public ResponseEntity<Void> handle(
      @PathVariable String workScheduleId,
      @RequestBody List<WeekdayScheduleDto> body) {
    var useCase = new EditWeekScheduleUseCase(workSchedulesRepository);
    useCase.execute(workScheduleId, body);
    return ResponseEntity.noContent().build();
  }
}
