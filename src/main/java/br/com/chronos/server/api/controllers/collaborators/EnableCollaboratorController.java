package br.com.chronos.server.api.controllers.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.EnableCollaboratorUseCase;

@CollaboratorsController
public class EnableCollaboratorController {

  @Autowired
  private CollaboratorsRepository repository;

  @PostMapping("/enable/{id}")
  public ResponseEntity<CollaboratorDto> handle(@PathVariable("id") String collaboratorId) {
    var useCase = new EnableCollaboratorUseCase(repository);
    useCase.execute(collaboratorId);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
