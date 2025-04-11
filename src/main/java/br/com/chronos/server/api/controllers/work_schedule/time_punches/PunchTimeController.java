package br.com.chronos.server.api.controllers.work_schedule.time_punches;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.Data;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.interfaces.brokers.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.PunchTimeUseCase;

@TimePunchesController
public class PunchTimeController {
  @Autowired
  private TimePunchesRepository timePunchesRepository;

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private WorkScheduleBroker workScheduleBroker;

  @Data
  private static class Request {
    private LocalTime time;
  }

  @PatchMapping("/{timePunchId}")
  public ResponseEntity<TimePunchDto> handle(@PathVariable String timePunchId, @RequestBody Request body) {
    var useCase = new PunchTimeUseCase(timePunchesRepository, workdayLogsRepository, workScheduleBroker);
    var timePunchDto = useCase.execute(timePunchId, body.getTime());
    return ResponseEntity.ok(timePunchDto);
  }
}
