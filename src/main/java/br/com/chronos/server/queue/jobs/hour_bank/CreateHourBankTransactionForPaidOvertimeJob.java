package br.com.chronos.server.queue.jobs.hour_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CreateHourBankTransactionForPaidOvertimeUseCase;
import br.com.chronos.core.portal.domain.events.PaidOvertimeSolicitationApprovedEvent;

@Component
public class CreateHourBankTransactionForPaidOvertimeJob {
  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  public void handle(PaidOvertimeSolicitationApprovedEvent.Payload payload) {
    var useCase = new CreateHourBankTransactionForPaidOvertimeUseCase(hourBankTransactionsRepository);
    useCase.execute(payload.collaboratorId());
  }
}
