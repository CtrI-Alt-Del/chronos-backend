package br.com.chronos.server.api.advices.hour_bank;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.EnsureSufficientHourBankCreditUseCase;
import br.com.chronos.server.api.advices.Advice;
import br.com.chronos.server.api.controllers.solicitation.solicitations.CreatePaidOvertimeSolitationController;

@ControllerAdvice
public class EnsureSufficientHourBankCreditForPaidOvertimeAdvice extends Advice {
  private static final LocalTime OVERTIME = LocalTime.of(2, 0);

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  public EnsureSufficientHourBankCreditForPaidOvertimeAdvice() {
    super(CreatePaidOvertimeSolitationController.class);
  }

  @Override
  protected Object handle(Object body) {
    var account = authenticationProvider.getAccount();
    var collaboratorId = account.getCollaboratorId().toString();
    var useCase = new EnsureSufficientHourBankCreditUseCase(hourBankTransactionsRepository);
    useCase.execute(collaboratorId, OVERTIME);
    return body;
  }

}
