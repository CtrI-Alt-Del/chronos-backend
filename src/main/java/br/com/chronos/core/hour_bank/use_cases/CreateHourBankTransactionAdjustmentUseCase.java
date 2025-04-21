package br.com.chronos.core.hour_bank.use_cases;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionReason;
import br.com.chronos.core.hour_bank.interfaces.HourBankBroker;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CreateHourBankTransactionAdjustmentUseCase {
  private final HourBankTransactionsRepository repository;
  private final HourBankBroker broker;

  public CreateHourBankTransactionAdjustmentUseCase(
      HourBankTransactionsRepository repository,
      HourBankBroker broker) {
    this.repository = repository;
    this.broker = broker;
  }

  public void execute(HourBankTransactionDto hourBankTransactionDto, String collaboratorIdValue) {
    hourBankTransactionDto.setReason(HourBankTransactionReason.createAsAdjustment().toString());
    var collaboratorId = Id.create(collaboratorIdValue);
    var hourBankTransaction = HourBankTransaction.create(hourBankTransactionDto);
    repository.add(hourBankTransaction, collaboratorId);

    var event = new HourBankTransactionCreatedEvent(hourBankTransaction, collaboratorId);
    broker.publish(event);
  }
}
