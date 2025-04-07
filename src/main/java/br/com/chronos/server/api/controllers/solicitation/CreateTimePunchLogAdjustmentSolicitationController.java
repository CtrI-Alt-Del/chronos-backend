package br.com.chronos.server.api.controllers.solicitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.solicitation.use_cases.CreateTimePunchLogAdjustmentSolicitationUseCase;

@SolicitationsController
public class CreateTimePunchLogAdjustmentSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/time-punch-adjustment")
  public ResponseEntity<TimePunchLogAdjustmentSolicitationDto> handle(
      @RequestBody TimePunchLogAdjustmentSolicitationDto body) {
    var useCase = new CreateTimePunchLogAdjustmentSolicitationUseCase(solicitationsRepository);
    var responsible = authenticationProvider.getAuthenticatedUser();
    var senderId = responsible.getCollaboratorId();
    var timePunchLogAdjustmentSolicitationDto = useCase.execute(body, senderId);
    return ResponseEntity.ok(timePunchLogAdjustmentSolicitationDto);
  }
}
