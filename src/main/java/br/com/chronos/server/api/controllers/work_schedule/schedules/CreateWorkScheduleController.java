package br.com.chronos.server.api.controllers.work_schedule.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.CreateWorkScheduleUseCase;

@WorkSchedulesController
public class CreateWorkScheduleController {

  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @PostMapping
  public ResponseEntity<WorkScheduleDto> handle(@RequestBody WorkScheduleDto body) {
    var useCase = new CreateWorkScheduleUseCase(workSchedulesRepository);
    var workScheduleDto = useCase.execute(body);
    return ResponseEntity.status(HttpStatus.CREATED).body(workScheduleDto);
  }
}
