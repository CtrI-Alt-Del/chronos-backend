package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.DisableCollaboratorUseCase;

@CollaboratorsController
public class DisableCollaboratorController {

  @Autowired
  private CollaboratorsRepository repository;

  @PatchMapping("/{id}/disable")
  public ResponseEntity<Void> handle(@PathVariable("id") String collaboratorId) {
    var useCase = new DisableCollaboratorUseCase(repository);
    useCase.execute(collaboratorId);
    return ResponseEntity.noContent().build();
  }
}
