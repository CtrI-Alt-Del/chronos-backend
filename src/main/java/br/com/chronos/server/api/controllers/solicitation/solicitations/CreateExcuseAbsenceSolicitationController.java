package br.com.chronos.server.api.controllers.solicitation.solicitations;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.solicitation.domain.dtos.ExcuseAbsenceSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.solicitation.use_cases.CreateExcuseAbsenceSolicitationUseCase;

@SolicitationsController
public class CreateExcuseAbsenceSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/excuse-absence")
  public ResponseEntity<ExcuseAbsenceSolicitationDto> handle(@RequestBody ExcuseAbsenceSolicitationDto body) {
    var useCase = new CreateExcuseAbsenceSolicitationUseCase(solicitationsRepository);
    var responsible = authenticationProvider.getAccount();
    var senderId = responsible.getCollaboratorId();
    var response = useCase.execute(body, senderId);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
