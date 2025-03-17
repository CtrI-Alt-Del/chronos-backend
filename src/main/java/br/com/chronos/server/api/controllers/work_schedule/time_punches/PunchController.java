package br.com.chronos.server.api.controllers.work_schedule.time_punches;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.PunchUseCase;
import lombok.Data;

import org.springframework.web.bind.annotation.PatchMapping;

@TimePunchesController
public class PunchController {

  @Autowired
  private TimePunchesRepository timePunchesRepository;

  @Data
  private static class Body {
    private String timePunchId;
    private LocalTime punch;
  }

  @PatchMapping
  public ResponseEntity<Void> handle(@RequestBody Body body) {
    var useCase = new PunchUseCase(timePunchesRepository);
    useCase.execute(body.getTimePunchId(), body.getPunch());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
