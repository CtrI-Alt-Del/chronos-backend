package br.com.chronos.server.api.controllers.work_schedule.schedules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.ListWorkSchedulesUseCase;

@WorkSchedulesController
public class ListWorkSchedulesController {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @GetMapping
  public ResponseEntity<List<WorkScheduleDto>> handle() {
    var useCase = new ListWorkSchedulesUseCase(workSchedulesRepository);
    var workdaySchedules = useCase.execute();
    return ResponseEntity.ok(workdaySchedules);
  }
}
