package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.UpdateCollaboratorUseCase;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import lombok.Data;

@CollaboratorsController
public class UpdateCollaboratorController {

  @Autowired
  private CollaboratorsRepository repository;
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Data
  private static class Request {
    CollaboratorDto collaboratorDto;
    String workScheduleId;
  }

  @PutMapping("/{id}")
  public ResponseEntity<CollaboratorDto> handle(@PathVariable("id") String collaboratorId,
      @RequestBody Request body) {
    var useCase = new UpdateCollaboratorUseCase(repository);
    var responsible = authenticationProvider.getAuthenticatedUser();
    var responsibleSector = responsible.getSector().value();
    var responsibleRole = responsible.getRole();
    Id workScheduleId = body.workScheduleId != null ? Id.create(body.workScheduleId) : null;
    var response = useCase.execute(collaboratorId, body.collaboratorDto,  workScheduleId,responsibleSector,responsibleRole);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
