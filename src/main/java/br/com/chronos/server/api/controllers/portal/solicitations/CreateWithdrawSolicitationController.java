
package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.CreateWithdrawSolicitationUseCase;

@SolicitationsController
public class CreateWithdrawSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private PortalBroker broker;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/withdraw")
  public ResponseEntity<WorkLeaveSolicitationDto> handle(@RequestBody WorkLeaveSolicitationDto body) {
    var useCase = new CreateWithdrawSolicitationUseCase(solicitationsRepository, broker);
    var account = authenticationProvider.getAccount();
    var senderResponsibleId = account.getCollaboratorId().toString();
    var response = useCase.execute(body, senderResponsibleId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
