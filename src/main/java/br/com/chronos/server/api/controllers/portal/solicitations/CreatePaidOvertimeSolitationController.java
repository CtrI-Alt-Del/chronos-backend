package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.portal.domain.dtos.PaidOvertimeSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.CreatePaidOvertimeSolicitationUseCase;

@SolicitationsController
public class CreatePaidOvertimeSolitationController {
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class Request extends PaidOvertimeSolicitationDto {
  }

  @PostMapping("/paid-overtime")
  public ResponseEntity<Void> handle(@RequestBody Request body) {
    var account = authenticationProvider.getAccount();
    var senderResponsibleId = account.getCollaboratorId().toString();
    var useCase = new CreatePaidOvertimeSolicitationUseCase(solicitationsRepository);
    useCase.execute(body, senderResponsibleId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
