package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.portal.domain.dtos.TimePunchAdjustmentSolicitationDto;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.CreateTimePunchAdjustmentSolicitationUseCase;

@SolicitationsController
public class CreateTimePunchAdjustmentSolicitationController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private PortalBroker portalBroker;

  @PostMapping("/time-punch-adjustment")
  public ResponseEntity<TimePunchAdjustmentSolicitationDto> handle(
      @RequestBody TimePunchAdjustmentSolicitationDto body) {
    var useCase = new CreateTimePunchAdjustmentSolicitationUseCase(
        solicitationsRepository,
        portalBroker);
    var account = authenticationProvider.getAccount();
    var senderResponsibleId = account.getCollaboratorId().toString();
    var response = useCase.execute(
        body,
        senderResponsibleId,
        account.getCollaborationSector().toString());
    return ResponseEntity.ok(response);
  }
}
