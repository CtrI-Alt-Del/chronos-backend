package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.notification.use_cases.SendSolicitationApprovalEmailUseCase;
import br.com.chronos.core.portal.domain.events.SolicitationApprovedEvent;

@Component
public class SendSolicitationApprovalEmailJob {
  public static final String KEY = "notification/send.solicitation.approval.email.job";

  @Autowired
  private EmailProvider emailProvider;

  public void handle(SolicitationApprovedEvent.Payload payload) {
    var useCase = new SendSolicitationApprovalEmailUseCase(emailProvider);
    useCase.execute(payload.employeeName(), payload.employeeEmail(), payload.solicitationType());
  }
}
