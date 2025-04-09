package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.auth.domain.records.Password;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.CreateCollaboratorUseCase;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    var collaboratorId = useCase.execute(body.collaboratorDto, password, account.getSector());
    return ResponseEntity.ok(new Response(collaboratorId));
  }
}
