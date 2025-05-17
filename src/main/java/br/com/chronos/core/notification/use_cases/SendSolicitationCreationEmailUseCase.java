package br.com.chronos.core.notification.use_cases;

import java.util.List;

import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.notification.interfaces.EmailProvider;

public class SendSolicitationCreationEmailUseCase {
  private final EmailProvider emailProvider;

  public SendSolicitationCreationEmailUseCase(EmailProvider emailProvider) {
    this.emailProvider = emailProvider;
  }

  public void execute(
      List<String> managersEmails,
      String employeeName,
      String solicitationType) {
    for (String managerEmail : managersEmails) {
      emailProvider.sendSolicitationCreationEmail(
          Email.create(managerEmail),
          Text.create(employeeName, "nome do colaborador"),
          Text.create(solicitationType, "tipo de solicitação"));
    }
  }
}
