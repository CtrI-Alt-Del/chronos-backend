package br.com.chronos.core.notification.use_cases;

import java.util.List;

import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.notification.interfaces.EmailProvider;

public class SendSolicitationDeniedEmailUseCase {
  private final EmailProvider emailProvider;

  public SendSolicitationDeniedEmailUseCase(EmailProvider emailProvider) {
    this.emailProvider = emailProvider;
  }

  public void execute(List<String> managersEmails, String solicitationType) {
    for (String managerEmail : managersEmails) {
      emailProvider.sendSolicitationDeniedEmail(
          Email.create(managerEmail),
          Text.create(solicitationType, "tipo de solicitação"));
    }
  }
}
