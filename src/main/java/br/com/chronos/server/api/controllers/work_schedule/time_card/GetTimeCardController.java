package br.com.chronos.server.api.controllers.work_schedule.time_card;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.work_schedule.domain.dtos.TimeCardRowDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.GetTimeCardUseCase;

@TimeCardController
public class GetTimeCardController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/{collaboratorId}")
  ResponseEntity<List<TimeCardRowDto>> handle(
      @PathVariable String collaboratorId,
      @RequestParam int month,
      @RequestParam int year) {
    var useCase = new GetTimeCardUseCase(workdayLogsRepository);
    var timeCard = useCase.execute(collaboratorId, month, year);
    return ResponseEntity.ok(timeCard);
  }
}
