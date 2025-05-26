package br.com.chronos.core.notification.use_cases;

import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.notification.interfaces.EmailProvider;

public class SendSolicitationApprovalEmailUseCase {
  private final EmailProvider emailProvider;

  public SendSolicitationApprovalEmailUseCase(EmailProvider emailProvider) {
    this.emailProvider = emailProvider;
  }

  public void execute(String employeeName, String employeeEmail, String solicitationType) {
    System.out.println("Sending email to " + employeeEmail);
    try {
      emailProvider.sendSolicitationApprovalEmail(
          Email.create(employeeEmail),
          Text.create(employeeName, "nome do colaborador"),
          Text.create(solicitationType, "tipo de solicitação"));
    } catch (Exception e) {
      System.out.println("Error sending email: " + e.getMessage());
    }
  }
}
