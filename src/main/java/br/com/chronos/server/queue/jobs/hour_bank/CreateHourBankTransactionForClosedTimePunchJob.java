package br.com.chronos.server.queue.jobs.hour_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CreateHourBankTransactionForClosedTimePunchUseCase;
import br.com.chronos.core.work_schedule.domain.events.TimePunchClosedEvent;

@Component
public class CreateHourBankTransactionForClosedTimePunchJob {
  @Autowired
  private HourBankTransactionsRepository hourBankTransactionRepository;

  public void handle(TimePunchClosedEvent.Payload payload) {
    var useCase = new CreateHourBankTransactionForClosedTimePunchUseCase(hourBankTransactionRepository);
    useCase.execute(payload.overtime, payload.latetime, payload.date, payload.collaboratorId);
  }
}
