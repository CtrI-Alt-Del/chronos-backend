package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.work_schedule.domain.dtos.WorkTimeLogDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.GetWorkTimeLogUseCase;

@WorkdayLogsController
public class GetWorkTimeLogController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/work-time/{collaboratorId}")
  public ResponseEntity<WorkTimeLogDto> execute(@PathVariable String collaboratorId) {
    var useCase = new GetWorkTimeLogUseCase(workdayLogsRepository);
    var workTimeLog = useCase.execute(collaboratorId);
    return ResponseEntity.ok(workTimeLog);
  }
}
