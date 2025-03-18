package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.GetTodayWorkdayLogUseCase;

@WorkdayLogsController
public class GetTodayWorkdayLogController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/{collaboratorId}")
  public ResponseEntity<WorkdayLogDto> handle(@RequestParam String collaboratorId) {
    var useCase = new GetTodayWorkdayLogUseCase(workdayLogsRepository);
    var workdayLogDto = useCase.execute(collaboratorId);
    return ResponseEntity.ok(workdayLogDto);
  }
}
