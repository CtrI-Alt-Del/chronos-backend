package br.com.chronos.server.api.controllers.work_schedule.schedules;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.EditWorkScheduleDescriptionUseCase;
import lombok.Data;

@WorkSchedulesController
public class EditWorkScheduleDescriptionController {
  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @Data
  private static class Request {
    private String description;
  }

  @PatchMapping("/{workScheduleId}/description")
  public ResponseEntity<TimePunchDto> handle(
      @PathVariable String workScheduleId,
      @RequestBody Request body) {
    var useCase = new EditWorkScheduleDescriptionUseCase(workSchedulesRepository);
    useCase.execute(workScheduleId, body.getDescription());
    return ResponseEntity.noContent().build();
  }
}
