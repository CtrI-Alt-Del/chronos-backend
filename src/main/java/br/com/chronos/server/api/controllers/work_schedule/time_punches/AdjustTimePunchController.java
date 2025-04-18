package br.com.chronos.server.api.controllers.work_schedule.time_punches;

import java.time.LocalTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.Data;

import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.AdjustTimePunchUseCase;

@TimePunchesController
public class AdjustTimePunchController {
  @Autowired
  private TimePunchesRepository timePunchesRepository;

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private WorkScheduleBroker workScheduleBroker;

  @Data
  private static class Request {
    private LocalTime time;
    private String period;
  }

  @PatchMapping("/{timePunchId}/adjust")
  public ResponseEntity<TimePunchDto> handle(@PathVariable String timePunchId, @RequestBody Request body) {
    var useCase = new AdjustTimePunchUseCase(timePunchesRepository, workdayLogsRepository, workScheduleBroker);
    useCase.execute(timePunchId, body.getTime(), body.getPeriod());
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
