package br.com.chronos.server.queue.jobs.collaboration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;
import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.UpdateCollaboratorUseCase;

@Component
public class UpdateCollaboratorJob {
  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  public void handle(AccountUpdatedEvent.Payload payload) {
    var collaboratorDto = new CollaboratorDto()
        .setId(payload.collaboratorId)
        .setName(payload.collaboratorName)
        .setCpf(payload.collaboratorCpf)
        .setWorkload(payload.collaboratorWorkload);

    System.out.println("payload" + payload.collaboratorWorkload);
    var useCase = new UpdateCollaboratorUseCase(collaboratorsRepository);
    useCase.execute(collaboratorDto);
  }
}
