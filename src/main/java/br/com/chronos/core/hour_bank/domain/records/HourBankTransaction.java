package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.records.DateTime;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;

public record HourBankTransaction(
    Time time,
    DateTime dateTime,
    HourBankTransactionOperation operation,
    HourBankTransactionReason reason) {
  public static HourBankTransaction create(HourBankTransactionDto dto) {
    var transactionTime = Time.create(dto.time);
    var transactionOperation = HourBankTransactionOperation.create(dto.operation);
    var transactionDate = DateTime.create(dto.dateTime);
    var transactionReason = HourBankTransactionReason.create(dto.reason);
    return new HourBankTransaction(transactionTime, transactionDate, transactionOperation, transactionReason);
  }

  public HourBankTransactionDto getDto() {
    return new HourBankTransactionDto()
        .setTime(time.value())
        .setDateTime(dateTime.value())
        .setReason(reason.toString())
        .setOperation(operation.toString());
  }

}
