package br.com.chronos.server.api.controllers.work_schedule.collaborator_schedules;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;

import br.com.chronos.core.modules.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.CollaboratorSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.CreateCollaboratorScheduleUseCase;
import br.com.chronos.server.api.controllers.work_schedule.schedules.WorkSchedulesController;

@WorkSchedulesController
public class CreateCollaboratorScheduleController {
  @Autowired
  private CollaboratorSchedulesRepository collaboratorSchedulesRepository;

  @Data
  private static class Request {
    private List<WeekdayScheduleDto> weekSchedule;
    private DayOffScheduleDto dayOffSchedule;
  }

  @PostMapping("/{collaboratorId}")
  public ResponseEntity<Void> handle(@PathVariable String collaboratorId, @RequestBody Request body) {
    var useCase = new CreateCollaboratorScheduleUseCase(collaboratorSchedulesRepository);
    useCase.execute(collaboratorId, body.getWeekSchedule(), body.getDayOffSchedule());
    return ResponseEntity.noContent().build();
  }
}
