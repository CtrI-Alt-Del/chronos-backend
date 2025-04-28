package br.com.chronos.server.api.controllers.solicitation.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.TimePunchLogAdjustmentRepository;
import br.com.chronos.core.solicitation.use_cases.CreateTimePunchLogAdjustmentSolicitationUseCase;

@SolicitationsController
public class CreateTimePunchLogAdjustmentSolicitationController {
  @Autowired
  private TimePunchLogAdjustmentRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/time-punch-adjustment")
  public ResponseEntity<TimePunchLogAdjustmentSolicitationDto> handle(
      @RequestBody TimePunchLogAdjustmentSolicitationDto body) {
    var useCase = new CreateTimePunchLogAdjustmentSolicitationUseCase(solicitationsRepository);
    var account = authenticationProvider.getAccount();
    var senderResponsibleId = account.getCollaboratorId().toString();
    var timePunchLogAdjustmentSolicitationDto = useCase.execute(body, senderResponsibleId);
    return ResponseEntity.ok(timePunchLogAdjustmentSolicitationDto);
  }
}
