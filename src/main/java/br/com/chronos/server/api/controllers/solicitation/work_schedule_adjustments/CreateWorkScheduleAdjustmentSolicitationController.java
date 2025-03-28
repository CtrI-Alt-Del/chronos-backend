package br.com.chronos.server.api.controllers.solicitation.work_schedule_adjustments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.solicitation.domain.dtos.WorkScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.use_cases.CreateWorkScheduleAdjustmentSolicitationUseCase;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

@WorkScheduleAdjustmentController
public class CreateWorkScheduleAdjustmentSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @PostMapping
  public ResponseEntity<WorkScheduleAdjustmentSolicitationDto> handle(
      @RequestBody WorkScheduleAdjustmentSolicitationDto body) {
    var useCase = new CreateWorkScheduleAdjustmentSolicitationUseCase(solicitationsRepository, workSchedulesRepository);
    var response = useCase.execute(body);
    return ResponseEntity.ok(response);
  }
}
