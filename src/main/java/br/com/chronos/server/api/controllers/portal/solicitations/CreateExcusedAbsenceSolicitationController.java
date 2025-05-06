package br.com.chronos.server.api.controllers.portal.solicitations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.portal.domain.dtos.ExcusedAbsenceSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.CreateExcusedAbsenceSolicitationUseCase;

@SolicitationsController
public class CreateExcusedAbsenceSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/excused-absence")
  public ResponseEntity<ExcusedAbsenceSolicitationDto> handle(@RequestBody ExcusedAbsenceSolicitationDto body) {
    var useCase = new CreateExcusedAbsenceSolicitationUseCase(solicitationsRepository);
    var account = authenticationProvider.getAccount();
    var senderResponsibleId = account.getCollaboratorId().toString();
    var response = useCase.execute(body, senderResponsibleId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
