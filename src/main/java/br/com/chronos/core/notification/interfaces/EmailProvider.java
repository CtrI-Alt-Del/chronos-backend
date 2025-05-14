package br.com.chronos.core.notification.interfaces;

import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;

public interface EmailProvider {
  void sendAuthenticationEmail(Email recipientEmail, Text otpCode);

  void sendSolicitationCreatedEmail(Email managerEmail, Text solicitationType);

  void sendSolicitationApprovedEmail(Email managerEmail, Text solicitationType);

  void sendSolicitationDeniedEmail(Email managerEmail, Text solicitationType);
}
