package br.com.chronos.server.queue.jobs.hour_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CreateHourBankTransactionForExcusedAbsenceUseCase;
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceExcusedEvent;

@Component
public class CreateHourBankTransactionForExcusedAbsenceJob {
  public static final String KEY = "hour.bank/create.hour.bank.transaction.for.excused.absence.job";

  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  public void handle(WorkdayAbsenceExcusedEvent.Payload payload) {
    var useCase = new CreateHourBankTransactionForExcusedAbsenceUseCase(hourBankTransactionsRepository);
    useCase.execute(payload.collaboratorId(), payload.hourBankDebit());
  }
}
