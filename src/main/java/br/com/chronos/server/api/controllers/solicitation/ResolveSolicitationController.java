package br.com.chronos.server.api.controllers.solicitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.use_cases.ResolveSolicitationUseCase;
import lombok.Data;

@SolicitationsController
public class ResolveSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Data
  private static class Body {
    private String feedbackMessage;
    private String status;
  }

  @PatchMapping("/resolve/{id}")
  public ResponseEntity<SolicitationDto> handle(@PathVariable("id") String solicitationId, @RequestBody Body body) {
    var useCase = new ResolveSolicitationUseCase(solicitationsRepository, collaboratorsRepository);
    var responsible = authenticationProvider.getAuthenticatedUser();
    var response = useCase.execute(solicitationId, responsible.getCollaboratorId().toString(), body.status,
        body.feedbackMessage);
    return ResponseEntity.ok(response);

  }
}
