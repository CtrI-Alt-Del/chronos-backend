package br.com.chronos.server.api.controllers.work_schedule.collaborator_schedules;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;

import br.com.chronos.core.modules.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.CreateCollaboratorScheduleUseCase;
import br.com.chronos.core.modules.work_schedule.use_cases.CreateWorkdayLogUseCase;

@CollaboratorScheduleController
public class CreateCollaboratorScheduleController {
  @Autowired
  private WeekdaySchedulesRepository weekdaySchedulesRepository;

  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Data
  private static class Request {
    private List<WeekdayScheduleDto> weekSchedule;
    private DayOffScheduleDto dayOffSchedule;
  }

  @PostMapping("/{collaboratorId}")
  public ResponseEntity<Void> handle(@PathVariable String collaboratorId, @RequestBody Request body) {
    var useCase = new CreateCollaboratorScheduleUseCase(weekdaySchedulesRepository, dayOffSchedulesRepository);
    useCase.execute(collaboratorId, body.getWeekSchedule(), body.getDayOffSchedule());
    createWorkdayLogUseCase(collaboratorId);
    return ResponseEntity.noContent().build();
  }

  private void createWorkdayLogUseCase(String collaboratorId) {
    var useCase = new CreateWorkdayLogUseCase(
        workdayLogsRepository,
        weekdaySchedulesRepository,
        dayOffSchedulesRepository);

    useCase.execute(collaboratorId);
  }
}
