package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.EnableCollaboratorUseCase;

@CollaboratorsController
public class EnableCollaboratorController {

  @Autowired
  private CollaboratorsRepository repository;

  @PatchMapping("/{collaboratorId}/enable")
  public ResponseEntity<Void> handle(@PathVariable String collaboratorId) {
    var useCase = new EnableCollaboratorUseCase(repository);
    useCase.execute(collaboratorId);
    return ResponseEntity.noContent().build();
  }
}
