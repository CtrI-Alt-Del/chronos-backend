package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

  @PatchMapping("/{id}/enable")
  public ResponseEntity<Void> handle(@PathVariable("id") String collaboratorId) {
    var useCase = new EnableCollaboratorUseCase(repository);
    var responsible = this.authenticationProvider.getAuthenticatedUser();
    var sector = responsible.getSector().value();
    var role = responsible.getRole();
    useCase.execute(collaboratorId, sector, role);
    return ResponseEntity.noContent().build();
  }
}
