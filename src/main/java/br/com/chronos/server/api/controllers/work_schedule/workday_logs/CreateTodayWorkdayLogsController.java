package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.CreateTodayWorkdayLogsUseCase;

@WorkdayLogsController
public class CreateTodayWorkdayLogsController {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @PostMapping("/today")
  public ResponseEntity<WorkdayLogDto> handle() {
    var useCase = new CreateTodayWorkdayLogsUseCase(workdayLogsRepository, workSchedulesRepository);
    useCase.execute();
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
