package br.com.chronos.server.api.controllers.work_schedule.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.DeleteWorkScheduleUseCase;

@WorkSchedulesController
public class DeleteWorkScheduleController {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @DeleteMapping("/{workScheduleId}")
  public ResponseEntity<WorkScheduleDto> handle(@RequestParam String workScheduleId) {
    var useCase = new DeleteWorkScheduleUseCase(workSchedulesRepository);
    useCase.execute(workScheduleId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
