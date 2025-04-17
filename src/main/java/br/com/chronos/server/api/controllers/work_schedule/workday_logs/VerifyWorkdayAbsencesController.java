package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.VerifyWorkdayAbsencesUseCase;

@WorkdayLogsController
public class VerifyWorkdayAbsencesController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @PutMapping("/absences")
  public ResponseEntity<WorkdayLogDto> handle() {
    var useCase = new VerifyWorkdayAbsencesUseCase(workdayLogsRepository);
    useCase.execute(LocalDate.now());
    return ResponseEntity.noContent().build();
  }
}
