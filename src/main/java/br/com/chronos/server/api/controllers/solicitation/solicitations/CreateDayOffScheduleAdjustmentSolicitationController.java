package br.com.chronos.server.api.controllers.solicitation.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.solicitation.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.solicitation.use_cases.CreateDayOffScheduleAdjustmentSolicitationUseCase;

@SolicitationsController
public class CreateDayOffScheduleAdjustmentSolicitationController {

  @Autowired
  private DayOffScheduleAdjustmentRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/day-off-schedule-adjustment")
  public ResponseEntity<DayOffScheduleAdjustmentSolicitationDto> handle(
      @RequestBody DayOffScheduleAdjustmentSolicitationDto body) {
    var useCase = new CreateDayOffScheduleAdjustmentSolicitationUseCase(solicitationsRepository);
    var account = authenticationProvider.getAccount();
    var senderResponsibleId = account.getCollaboratorId().toString();
    var response = useCase.execute(body, senderResponsibleId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
