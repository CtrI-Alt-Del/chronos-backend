package br.com.chronos.server.queue.jobs.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.auth.use_cases.UpdateAccountUseCase;
import br.com.chronos.core.collaboration.domain.events.CollaboratorUpdatedEvent;

@Component
public class UpdateAccountJob{
  @Autowired
  private AccountsRepository accountsRepository;
  public void handle(CollaboratorUpdatedEvent.Payload payload){
    var accountDto = new AccountDto()
        .setEmail(payload.email)
        .setCollaborationSector(payload.sector)
        .setRole(payload.role)
        .setCollaboratorId(payload.collaboratorId);
    var useCase = new UpdateAccountUseCase(accountsRepository);
    useCase.execute(accountDto, payload.collaboratorId);
  }
}
