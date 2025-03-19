package br.com.chronos.server.api.controllers.work_schedule.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.ListWorkSchedulesUseCase;

@WorkSchedulesController
public class ListWorkSchedulesController {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @GetMapping
  public ResponseEntity<PaginationResponse<WorkScheduleDto>> handle(@RequestParam(defaultValue = "1") int page) {
    var useCase = new ListWorkSchedulesUseCase(workSchedulesRepository);
    var workdaySchedules = useCase.execute(page);
    return ResponseEntity.ok(workdaySchedules);
  }
}
