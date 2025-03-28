package br.com.chronos.server.api.controllers.solicitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationStatus;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationStatus.Status;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.use_cases.ResolveSolicitationUseCase;

@SolicitationsController
public class ResolveSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @PostMapping("/resolve/{id}")
  public ResponseEntity<SolicitationDto> handle(@PathVariable("id") String solicitationId,@RequestBody SolicitationStatus status) {
    System.out.println(status);
    var useCase = new ResolveSolicitationUseCase(solicitationsRepository,collaboratorsRepository);
    var responsible = authenticationProvider.getAuthenticatedUser();
    var response = useCase.execute(solicitationId, responsible.getCollaboratorId().toString(), status);
    return ResponseEntity.ok(response);

  }
}
