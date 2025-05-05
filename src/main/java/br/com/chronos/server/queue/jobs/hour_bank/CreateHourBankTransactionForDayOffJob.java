package br.com.chronos.server.queue.jobs.hour_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CreateHourBankTransactionForDayOffUseCase;
import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;

@Component
public class CreateHourBankTransactionForDayOffJob {
  public static final String KEY = "hour_bank/create.hour.bank.transaction.for.day.off.job";

  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  public void handle(DayOffSolicitationApprovedEvent.Payload payload) {
    var useCase = new CreateHourBankTransactionForDayOffUseCase(hourBankTransactionsRepository);
    useCase.execute(payload.collaboratorId(), payload.collaboratorWorkload());
  }
}
