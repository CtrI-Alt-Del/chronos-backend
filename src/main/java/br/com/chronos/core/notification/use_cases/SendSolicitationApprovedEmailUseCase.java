package br.com.chronos.core.notification.use_cases;

import java.util.List;

import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.notification.interfaces.EmailProvider;

public class SendSolicitationApprovedEmailUseCase {
  private final EmailProvider emailProvider;

  public SendSolicitationApprovedEmailUseCase(EmailProvider emailProvider) {
    this.emailProvider = emailProvider;
  }

  public void execute(List<String> managersEmails, String solicitationType) {
    for (String managerEmail : managersEmails) {
      emailProvider.sendSolicitationApprovedEmail(
          Email.create(managerEmail),
          Text.create(solicitationType, "tipo de solicitação"));
    }
  }
}
