package br.com.chronos.core.notification.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.notification.interfaces.EmailProvider;

public class SendUnexcusedWorkdayAbsenceEmailUseCase {
  private final EmailProvider emailProvider;

  public SendUnexcusedWorkdayAbsenceEmailUseCase(EmailProvider emailProvider) {
    this.emailProvider = emailProvider;
  }

  public void execute(String collaboratorEmail, String collaboratorName, LocalDate date) {
    emailProvider.sendUnexcusedWorkdayAbsenceEmail(
        Email.create(collaboratorEmail),
        Text.create(collaboratorName, "nome do colaborador"),
        Date.create(date));
  }
}
