package br.com.chronos.server.api.controllers.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.CreateCollaboratorUseCase;

@CollaboratorsController
public class CreateCollaboratorController {

  @Autowired
  private CollaboratorsRepository repository;


  @PostMapping
  public ResponseEntity<CollaboratorDto> handle(@RequestBody CollaboratorDto body) {
    var useCase = new CreateCollaboratorUseCase(repository);
    var collaboratorDto = useCase.execute(body);
    return ResponseEntity.status(HttpStatus.OK).body(collaboratorDto);
  }
}
