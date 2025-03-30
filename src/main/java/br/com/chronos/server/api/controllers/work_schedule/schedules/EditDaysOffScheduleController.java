package br.com.chronos.server.api.controllers.work_schedule.schedules;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.EditDaysOffScheduleUseCase;

@WorkSchedulesController
public class EditDaysOffScheduleController {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @Data
  private static class Request {
    private int workdaysCount;
    private int daysOffCount;
    private List<LocalDate> daysOff;
  }

  @PutMapping("/{workScheduleId}/days-off")
  public ResponseEntity<WorkScheduleDto> handle(@PathVariable String workScheduleId, @RequestBody Request body) {
    var useCase = new EditDaysOffScheduleUseCase(workSchedulesRepository);
    useCase.execute(workScheduleId, body.getWorkdaysCount(), body.getDaysOffCount(), body.getDaysOff());
    return ResponseEntity.noContent().build();
  }
}
