package br.com.chronos.server.api.advices.hour_bank;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.EnsureSufficientHourBankCreditUseCase;
import br.com.chronos.server.api.advices.Advice;
import br.com.chronos.server.api.controllers.portal.solicitations.CreateDayOffSolicitationController;

@ControllerAdvice
@Order(2)
public class EnsureSufficientHourBankCreditForDayOffSolicitationAdvice extends Advice {
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  public EnsureSufficientHourBankCreditForDayOffSolicitationAdvice() {
    super(CreateDayOffSolicitationController.class);
  }

  @Override
  protected Object handle(Object body) {
    if (body instanceof CreateDayOffSolicitationController.Request) {
      var request = (CreateDayOffSolicitationController.Request) body;
      var account = authenticationProvider.getAccount();
      var collaboratorId = account.getCollaboratorId().toString();
      var hourBankCredit = Duration.ofHours(request.workload);
      var useCase = new EnsureSufficientHourBankCreditUseCase(hourBankTransactionsRepository);
      useCase.execute(collaboratorId, hourBankCredit);
      return body;
    }

    return body;
  }

}
