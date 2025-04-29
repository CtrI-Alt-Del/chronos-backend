package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import java.time.LocalTime;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.Data;

import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.PunchTimeUseCase;

@WorkdayLogsController
public class PunchTimeController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private WorkScheduleBroker workScheduleBroker;

  @Data
  private static class Request {
    private LocalTime time;
  }

  @PatchMapping("/{workdayLogId}/time-punch")
  public ResponseEntity<TimePunchDto> handle(@PathVariable String workdayLogId, @RequestBody Request body) {
    var useCase = new PunchTimeUseCase(workdayLogsRepository, workScheduleBroker);
    var timePunchDto = useCase.execute(workdayLogId, body.getTime());
    return ResponseEntity.ok(timePunchDto);
  }
}
