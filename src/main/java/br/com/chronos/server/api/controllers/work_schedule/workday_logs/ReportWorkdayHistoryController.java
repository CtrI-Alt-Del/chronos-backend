package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.ReportWorkdayHistoryUseCase;

@WorkdayLogsController
public class ReportWorkdayHistoryController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/history")
  public ResponseEntity<PaginationResponse<WorkdayLogDto>> execute(
      @RequestParam LocalDate date,
      @RequestParam(defaultValue = "1") int page) {
    var useCase = new ReportWorkdayHistoryUseCase(workdayLogsRepository);
    var workdayHistory = useCase.execute(date, page);
    return ResponseEntity.ok(workdayHistory);
  }
}
