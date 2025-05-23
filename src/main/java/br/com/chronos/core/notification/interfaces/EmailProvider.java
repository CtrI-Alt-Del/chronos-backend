package br.com.chronos.core.notification.interfaces;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;

public interface EmailProvider {
  void sendAuthenticationEmail(Email recipientEmail, Text otpCode);

  void sendUnexcusedWorkdayAbsenceEmail(Email employeeEmail, Text employeeName, Date workdayDate);

  void sendSolicitationCreationEmail(Email managerEmail, Text employeeName, Text solicitationType);

  void sendSolicitationApprovalEmail(Email employeeEmail, Text employeeName, Text solicitationType);

  void sendSolicitationDenialEmail(Email employeeEmail, Text employeeName, Text solicitationType);
}
