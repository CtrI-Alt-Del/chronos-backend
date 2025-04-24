package br.com.chronos.core.hour_bank.interfaces;

import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;

public interface HourBankBroker {
  void publish(HourBankTransactionCreatedEvent event);
}
