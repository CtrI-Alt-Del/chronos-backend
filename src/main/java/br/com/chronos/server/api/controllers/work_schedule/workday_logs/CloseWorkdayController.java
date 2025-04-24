package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.CloseWorkdayUseCase;

@WorkdayLogsController
public class CloseWorkdayController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private WorkScheduleBroker workScheduleBroker;

  @PutMapping("/absences")
  public ResponseEntity<WorkdayLogDto> handle() {
    var useCase = new CloseWorkdayUseCase(workdayLogsRepository, workScheduleBroker);
    useCase.execute(LocalDate.now());
    return ResponseEntity.noContent().build();
  }
}
