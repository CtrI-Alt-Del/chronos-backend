package br.com.chronos.server.api.advices.collaboration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.GetCollaboratorUseCase;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.server.api.advices.Advice;
import br.com.chronos.server.api.controllers.portal.solicitations.CreateDayOffSolicitationController;

@ControllerAdvice
@Order(1)
public class AttachCollaboratorWorkloadAdvice extends Advice {
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  public AttachCollaboratorWorkloadAdvice() {
    super(CreateDayOffSolicitationController.class);
  }

  @Override
  protected Object handle(Object body) {
    if (body instanceof CreateDayOffSolicitationController.Request) {
      var request = (CreateDayOffSolicitationController.Request) body;
      var account = authenticationProvider.getAccount();
      var useCase = new GetCollaboratorUseCase(collaboratorsRepository);
      var collaborator = useCase.execute(account.getCollaboratorId().toString());
      request.setWorkload(collaborator.workload);
      return request;
    }

    return body;
  }
}
