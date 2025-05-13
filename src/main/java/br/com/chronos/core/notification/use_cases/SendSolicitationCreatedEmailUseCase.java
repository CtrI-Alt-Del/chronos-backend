package br.com.chronos.core.notification.use_cases;

import java.util.List;

import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.notification.interfaces.EmailProvider;

public class SendSolicitationCreatedEmailUseCase {
  private final EmailProvider emailProvider;

  public SendSolicitationCreatedEmailUseCase(EmailProvider emailProvider) {
    this.emailProvider = emailProvider;
  }

  public void execute(List<String> managersEmails, String solicitationType) {
    for (String managerEmail : managersEmails) {
      emailProvider.sendSolicitationCreatedEmail(
          Email.create(managerEmail),
          Text.create(solicitationType, "tipo de solicitação"));
    }
  }
}
