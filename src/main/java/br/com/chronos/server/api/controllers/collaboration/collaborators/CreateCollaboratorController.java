package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import br.com.chronos.core.auth.domain.records.Password;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.CreateCollaboratorUseCase;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

@CollaboratorsController
public class CreateCollaboratorController {
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private AccountsRepository accountsRepository;

  @Data
  private static class Request {
    private CollaboratorDto collaboratorDto;
    private String password;
  }

  @Data
  @AllArgsConstructor
  private static class Response {
    private String collaboratorId;
  }

  @PostMapping
  public ResponseEntity<Response> handle(@RequestBody Request body) {
    var useCase = new CreateCollaboratorUseCase(accountsRepository, collaboratorsRepository, authenticationProvider);
    var password = Password.create(body.password);
    var account = authenticationProvider.getAccount();
    var collaboratorId = useCase.execute(body.collaboratorDto, password, account.getSector().toString());
    return ResponseEntity.ok(new Response(collaboratorId));
  }
}
