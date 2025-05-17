package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.portal.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.CreateDayOffSolicitationUseCase;

@SolicitationsController
public class CreateDayOffSolicitationController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private PortalBroker portalBroker;

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class Request extends DayOffSolicitationDto {
  }

  @PostMapping("/day-off")
  public ResponseEntity<DayOffSolicitationDto> handle(@RequestBody Request body) {
    var useCase = new CreateDayOffSolicitationUseCase(solicitationsRepository, portalBroker);
    var account = authenticationProvider.getAccount();
    var senderResponsibleId = account.getCollaboratorId().toString();
    var response = useCase.execute(
        body,
        senderResponsibleId,
        account.getEmail().toString(),
        account.getCollaborationSector().toString());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
