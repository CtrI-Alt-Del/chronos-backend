package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.ReportWorkdayHistoryUseCase;

@WorkdayLogsController
public class ReportWorkdayHistoryController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/history")
  public ResponseEntity<List<WorkdayLogDto>> execute(@RequestParam LocalDate date, @RequestParam int page) {
    var useCase = new ReportWorkdayHistoryUseCase(workdayLogsRepository);
    var workdayHistory = useCase.execute(date, page);
    return ResponseEntity.ok(workdayHistory);
  }
}
