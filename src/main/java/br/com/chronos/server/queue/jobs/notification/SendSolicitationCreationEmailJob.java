package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.notification.use_cases.SendSolicitationCreationEmailUseCase;
import br.com.chronos.core.portal.domain.events.SolicitationCreatedEvent;
import br.com.chronos.server.api.services.CollaborationService;

@Component
public class SendSolicitationCreationEmailJob {
  public static final String KEY = "notification/send.solicitation.creation.email.job";

  @Autowired
  private CollaborationService collaborationService;

  @Autowired
  private EmailProvider emailProvider;

  public void handle(SolicitationCreatedEvent.Payload payload) {
    var managersEmails = collaborationService.getManagersEmails(payload.collaboratorationSector());
    var collaborator = collaborationService.getCollaborator(payload.employeeId());
    var useCase = new SendSolicitationCreationEmailUseCase(emailProvider);
    useCase.execute(managersEmails, collaborator.name, payload.solicitationType());
  }
}
