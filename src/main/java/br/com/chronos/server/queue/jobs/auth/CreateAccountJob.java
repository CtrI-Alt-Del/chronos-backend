package br.com.chronos.server.queue.jobs.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.use_cases.CreateAccountUseCase;
import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

@Component
public class CreateAccountJob {
  public static final String KEY = "auth/create.account.job";

  @Autowired
  private AccountsRepository accountsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  public void handle(CollaboratorCreatedEvent.Payload payload) {
    var accountDto = new AccountDto()
        .setPassword(payload.password)
        .setEmail(payload.email)
        .setCollaborationSector(payload.collaborationSector)
        .setRole(payload.role)
        .setPassword(payload.password)
        .setCollaboratorId(payload.collaboratorId);
    var useCase = new CreateAccountUseCase(accountsRepository, authenticationProvider);
    useCase.execute(accountDto);
  }
}
