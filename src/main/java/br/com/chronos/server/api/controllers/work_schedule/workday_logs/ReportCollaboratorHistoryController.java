package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.ReportCollaboratorHistoryUseCase;

@WorkdayLogsController
public class ReportCollaboratorHistoryController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/history/{collaboratorId}")
  ResponseEntity<PaginationResponse<WorkdayLogDto>> handle(
      @PathVariable String collaboratorId,
      @RequestParam LocalDate startDate,
      @RequestParam LocalDate endDate,
      @RequestParam(defaultValue = "1") int page) {
    var useCase = new ReportCollaboratorHistoryUseCase(workdayLogsRepository);
    var workdayLogs = useCase.execute(collaboratorId, startDate, endDate, page);
    return ResponseEntity.status(HttpStatus.OK).body(workdayLogs);
  }
}
