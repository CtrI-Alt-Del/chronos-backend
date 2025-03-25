package br.com.chronos.server.api.controllers.work_schedule.time_punches;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.EditTimePunchScheduleUseCase;

@TimePunchesController
public class EditTimePunchScheduleController {
  @Autowired
  private TimePunchesRepository timePunchesRepository;

  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @PutMapping("/{timePunchId}")
  public ResponseEntity<TimePunchDto> handle(@PathVariable String timePunchId, @RequestBody TimePunchDto body) {
    body.setId(timePunchId);
    var useCase = new EditTimePunchScheduleUseCase(timePunchesRepository, workSchedulesRepository);
    var timePunchDto = useCase.execute(body);
    return ResponseEntity.ok(timePunchDto);
  }
}
