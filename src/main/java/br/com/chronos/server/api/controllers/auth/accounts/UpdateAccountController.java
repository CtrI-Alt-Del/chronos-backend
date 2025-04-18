package br.com.chronos.server.api.controllers.auth.accounts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.interfaces.AuthBroker;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.auth.use_cases.UpdateAccountUseCase;
import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

@AccountsController
public class UpdateAccountController {
  @Autowired
  private AccountsRepository repository;
  @Autowired
  private AuthenticationProvider authenticationProvider;
  @Autowired
  private AuthBroker authBroker;

  @Data
  private static class Response {
    private AccountDto account;
    private CollaboratorDto collaborator;
  }

  @PutMapping("/{collaboratorId}")
  public ResponseEntity<Void> handle(
      @PathVariable String collaboratorId,
      @RequestBody Response body) {
    var useCase = new UpdateAccountUseCase(repository, authBroker);
    var account = authenticationProvider.getAccount();
    useCase.execute(
        account.getDto(),
        body.getAccount().setCollaboratorId(collaboratorId),
        body.getCollaborator().name,
        body.getCollaborator().cpf,
        body.getCollaborator().workload);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
