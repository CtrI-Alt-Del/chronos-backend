package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.UpdateCollaboratorUseCase;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

@CollaboratorsController
public class UpdateCollaboratorController {
  @Autowired
  private CollaboratorsRepository repository;
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PutMapping("/{collaboratorId}")
  public ResponseEntity<CollaboratorDto> handle(
      @PathVariable String collaboratorId,
      @RequestBody CollaboratorDto body) {
    var useCase = new UpdateCollaboratorUseCase(repository);
    var account = authenticationProvider.getAccount();
    var response = useCase.execute(
        collaboratorId,
        body,
        account.getCollaborationSector().toString());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
