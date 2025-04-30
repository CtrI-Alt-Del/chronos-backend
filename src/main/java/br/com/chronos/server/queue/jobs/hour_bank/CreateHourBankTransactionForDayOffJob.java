package br.com.chronos.server.queue.jobs.hour_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CreateHourBankTransactionForDayOffUseCase;
import br.com.chronos.core.work_schedule.domain.events.DayOffScheduledEvent;

@Component
public class CreateHourBankTransactionForDayOffJob {
  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  public void handle(DayOffScheduledEvent.Payload payload) {
    var useCase = new CreateHourBankTransactionForDayOffUseCase(hourBankTransactionsRepository);
    useCase.execute(payload.collaboratorId(), payload.collaboratorWorkload());
  }
}
