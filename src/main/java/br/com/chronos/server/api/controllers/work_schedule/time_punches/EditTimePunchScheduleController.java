package br.com.chronos.server.api.controllers.work_schedule.time_punches;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.EditTimePunchScheduleUseCase;

import org.springframework.web.bind.annotation.PutMapping;

@TimePunchesController
public class EditTimePunchScheduleController {

  @Autowired
  private TimePunchesRepository timePunchesRepository;


  @PutMapping("/{timePunchId}")
  public ResponseEntity<TimePunchDto> handle(@RequestParam String timePunchId, @RequestBody TimePunchDto body) {
    body.setId(timePunchId);
    var useCase = new EditTimePunchScheduleUseCase(timePunchesRepository);
    var timePunchDto = useCase.execute(body);
    return ResponseEntity.status(HttpStatus.OK).body(timePunchDto);
  }
}
