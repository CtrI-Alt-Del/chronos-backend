package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.GetCollaborationSectorHistoryUseCase;

@WorkdayLogsController
public class GetCollaborationSectorHistoryController {
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/history")
  public ResponseEntity<PaginationResponse<WorkdayLogDto>> execute(
      @RequestParam LocalDate date,
      @RequestParam(defaultValue = "") String collaboratorName,
      @RequestParam(defaultValue = "1") int page) {
    var account = authenticationProvider.getAccount();
    var useCase = new GetCollaborationSectorHistoryUseCase(workdayLogsRepository);
    var workdayHistory = useCase.execute(
        date,
        collaboratorName,
        account.getCollaborationSector().toString(),
        page);
    return ResponseEntity.ok(workdayHistory);
  }
}
