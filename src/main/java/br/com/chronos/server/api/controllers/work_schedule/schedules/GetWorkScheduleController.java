package br.com.chronos.server.api.controllers.work_schedule.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.GetWorkScheduleUseCase;

@WorkSchedulesController
public class GetWorkScheduleController {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @GetMapping("/{workScheduleId}")
  public ResponseEntity<WorkScheduleDto> handle(@RequestParam String workScheduleId) {
    var useCase = new GetWorkScheduleUseCase(workSchedulesRepository);
    var workSchedule = useCase.execute(workScheduleId);
    return ResponseEntity.ok(workSchedule);
  }
}
