package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.EnableCollaboratorUseCase;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;

@CollaboratorsController
public class EnableCollaboratorController {

  @Autowired
  private CollaboratorsRepository repository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/enable/{id}")
  public ResponseEntity<CollaboratorDto> handle(@PathVariable("id") String collaboratorId) {
    var useCase = new EnableCollaboratorUseCase(repository);
    var responsibleEmail = this.authenticationProvider.getAuthenticatedUser().getEmail();
    useCase.execute(collaboratorId, responsibleEmail);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
