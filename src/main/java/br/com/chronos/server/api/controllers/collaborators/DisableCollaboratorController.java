package br.com.chronos.server.api.controllers.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.DisableCollaboratorUseCase;

@CollaboratorsController
public class DisableCollaboratorController {

  @Autowired
  private CollaboratorsRepository repository;

  @DeleteMapping("/disable/{id}")
  public ResponseEntity<CollaboratorDto> handle(@PathVariable("id") String collaboratorId) {
    var useCase = new DisableCollaboratorUseCase(repository);
    useCase.execute(collaboratorId);
    return ResponseEntity.status(HttpStatus.FOUND).body(null);
  }
}
