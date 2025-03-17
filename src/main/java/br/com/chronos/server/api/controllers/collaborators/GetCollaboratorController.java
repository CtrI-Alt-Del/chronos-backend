package br.com.chronos.server.api.controllers.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.GetCollaboratorUseCase;

@CollaboratorsController
public class GetCollaboratorController {

  @Autowired
  private CollaboratorsRepository repository;

  @GetMapping("/{id}")
  public ResponseEntity<CollaboratorDto> handle(@PathVariable("id") String collaboratorId) {
    var useCase = new GetCollaboratorUseCase(repository);
    var response = useCase.execute(collaboratorId);
    return ResponseEntity.status(HttpStatus.FOUND).body(response);
  }
}
