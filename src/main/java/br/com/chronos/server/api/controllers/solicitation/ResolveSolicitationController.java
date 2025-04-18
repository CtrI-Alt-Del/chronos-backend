package br.com.chronos.server.api.controllers.solicitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.solicitation.use_cases.ResolveSolicitationUseCase;
import lombok.Data;

@SolicitationsController
public class ResolveSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Data
  private static class Body {
    private String feedbackMessage;
    private String status;
    private String solicitationType;
  }

  @PatchMapping("/resolve/{id}")
  public ResponseEntity<SolicitationDto> handle(@PathVariable("id") String solicitationId, @RequestBody Body body) {
    var useCase = new ResolveSolicitationUseCase(solicitationsRepository);
    var responsible = authenticationProvider.getAccount();
    var collaboratorId = responsible.getCollaboratorId();
    var response = useCase.execute(solicitationId, collaboratorId, body.status,
        body.feedbackMessage, body.solicitationType);
    return ResponseEntity.ok(response);

  }
}
