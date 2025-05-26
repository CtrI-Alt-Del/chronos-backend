package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.CreateVacationSolicitationUseCase;

@SolicitationsController
public class CreateVacationSolicitationController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private PortalBroker broker;

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class Request extends WorkLeaveSolicitationDto {
  }

  @PostMapping("work-leave/vacation")
  public ResponseEntity<WorkLeaveSolicitationDto> handle(@RequestBody Request body) {
    var useCase = new CreateVacationSolicitationUseCase(solicitationsRepository, broker);
    var account = authenticationProvider.getAccount();
    var senderResponsibleId = account.getCollaboratorId().toString();
    var response = useCase.execute(body, senderResponsibleId, account.getCollaborationSector().toString());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
