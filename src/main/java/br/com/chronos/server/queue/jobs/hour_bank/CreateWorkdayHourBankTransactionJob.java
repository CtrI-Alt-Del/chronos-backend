package br.com.chronos.server.queue.jobs.hour_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.interfaces.HourBankBroker;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CreateWorkdayHourBankTransactionUseCase;
import br.com.chronos.core.work_schedule.domain.events.WorkdayCompletedEvent;

@Component
public class CreateWorkdayHourBankTransactionJob {
  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  @Autowired
  private HourBankBroker hourBankBroker;

  public void handle(WorkdayCompletedEvent.Payload payload) {
    var useCase = new CreateWorkdayHourBankTransactionUseCase(
        hourBankTransactionsRepository,
        hourBankBroker);

    useCase.execute(
        payload.overtime,
        payload.undertime,
        payload.latetime,
        payload.date,
        payload.collaboratorId);
  }
}
