package br.com.chronos.core.notification.use_cases;

import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.notification.interfaces.EmailProvider;

public class SendAuthenticationEmailUseCase {
  private final EmailProvider emailProvider;

  public SendAuthenticationEmailUseCase(EmailProvider emailProvider) {
    this.emailProvider = emailProvider;
  }

  public void execute(String recipientEmail, String otpCode) {
    emailProvider.sendAuthenticationEmail(
        Email.create(recipientEmail),
        Text.create(otpCode, "otp code"));
  }
}
