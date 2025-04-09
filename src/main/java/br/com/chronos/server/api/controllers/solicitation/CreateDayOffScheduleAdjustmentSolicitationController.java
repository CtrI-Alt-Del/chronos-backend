package br.com.chronos.server.api.controllers.solicitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.solicitation.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.interfaces.repository.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.modules.solicitation.use_cases.CreateDayOffScheduleAdjustmentSolicitationUseCase;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

@SolicitationsController
public class CreateDayOffScheduleAdjustmentSolicitationController {

  @Autowired
  private DayOffScheduleAdjustmentRepository solicitationsRepository;

  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/day-off-schedule-adjustment")
  public ResponseEntity<DayOffScheduleAdjustmentSolicitationDto> handle(
      @RequestBody DayOffScheduleAdjustmentSolicitationDto body) {
    var useCase = new CreateDayOffScheduleAdjustmentSolicitationUseCase(solicitationsRepository,
        dayOffSchedulesRepository);
    var responsible = authenticationProvider.getAccount();
    var senderId = responsible.getCollaboratorId();
    var response = useCase.execute(body, senderId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
