package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;
import lombok.Data;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.interfaces.CollaborationBroker;
import br.com.chronos.core.collaboration.interfaces.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.CreateCollaboratorUseCase;

@CollaboratorsController
public class CreateCollaboratorController {
  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private CollaborationBroker collaborationBroker;

  @Data
  private static class Request {
    private CollaboratorDto collaborator;
    private String accountPassword;
  }

  @Data
  @AllArgsConstructor
  private static class Response {
    private String collaboratorId;
  }

  @PostMapping
  public ResponseEntity<Response> handle(@RequestBody Request body) {
    var useCase = new CreateCollaboratorUseCase(collaboratorsRepository, collaborationBroker);
    var collaboratorId = useCase.execute(body.collaborator, body.accountPassword);
    return ResponseEntity.ok(new Response(collaboratorId));
  }
}
