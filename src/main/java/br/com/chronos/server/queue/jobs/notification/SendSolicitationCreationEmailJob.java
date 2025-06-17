package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${service.code}")
  private String serviceCode;

  public void handle(SolicitationCreatedEvent.Payload payload) {
    try {
      collaborationService.setJwt(
          "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjaHJvbm9zLXNlcnZlciIsInN1YiI6IntcImlkXCI6XCJmYzIxYzg1MC02NjhlLTQ4ZTEtOTU4OS1kZDY3MWNlNDg3MDVcIixcImVtYWlsXCI6XCJjaHJvbm9zLm1hbmFnZXJAZ21haWwuY29tXCIsXCJwYXNzd29yZFwiOlwiJDJhJDEwJHAwNEdEWGgxWVR5b0tuREx6OVRjZWV1YnZaWEZTc3NNLjZPY1dUd1Rrdlc2bnNCaGI4Q2ZhXCIsXCJpc0FjdGl2ZVwiOnRydWUsXCJyb2xlXCI6XCJtYW5hZ2VyXCIsXCJjb2xsYWJvcmF0aW9uU2VjdG9yXCI6XCJjb21lcmNpYWxcIixcImNvbGxhYm9yYXRvcklkXCI6XCI5YjFmZWJiMi02MGUyLTQzNjktODE0My1iNjk0MjliZjRmYjNcIn0iLCJleHAiOjE3NDg3MDUxMTl9.RubAIkUxvgCNmZRpUlnO8A_kyd8ucSeZsmiYoxHjNBA");
      var managersEmails = collaborationService.getManagersEmails(payload.collaboratorationSector());
      var collaborator = collaborationService.getCollaborator(payload.employeeId());
      var useCase = new SendSolicitationCreationEmailUseCase(emailProvider);
      useCase.execute(
      managersEmails,
      collaborator.name,
      payload.solicitationType());
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
