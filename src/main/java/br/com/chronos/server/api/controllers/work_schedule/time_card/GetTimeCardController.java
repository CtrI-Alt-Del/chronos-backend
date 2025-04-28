package br.com.chronos.server.api.controllers.work_schedule.time_card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.work_schedule.domain.dtos.TimeCardRowDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.GetTimeCardUseCase;

@TimeCardController
public class GetTimeCardController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/{collaboratorId}")
  ResponseEntity<List<TimeCardRowDto>> handle(@PathVariable String collaboratorId) {
    var useCase = new GetTimeCardUseCase(workdayLogsRepository);
    var workdayLogs = useCase.execute(collaboratorId);
    return ResponseEntity.ok(workdayLogs);
  }
}