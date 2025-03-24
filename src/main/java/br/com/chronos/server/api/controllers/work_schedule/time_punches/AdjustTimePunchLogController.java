package br.com.chronos.server.api.controllers.work_schedule.time_punches;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import lombok.Data;

import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.EditTimePunchScheduleUseCase;

public class AdjustTimePunchLogController {
  @Autowired
  private TimePunchesRepository timePunchesRepository;

  @Data
  private static class Request {
    private LocalTime punch;
  }

  @PatchMapping("/{timePunchId}/adjust")
  public ResponseEntity<TimePunchDto> handle(@PathVariable String timePunchId, @RequestBody TimePunchDto body) {
    body.setId(timePunchId);
    var useCase = new EditTimePunchScheduleUseCase(timePunchesRepository);
    var timePunchDto = useCase.execute(body);
    return ResponseEntity.ok(timePunchDto);
  }
}
