package br.com.chronos.core.hour_bank.domain.records.fakers;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.fakers.DateTimeFaker;
import br.com.chronos.core.global.domain.records.fakers.TimeFaker;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;

public class HourBankTransactionFaker {
  public static HourBankTransaction fake() {
    return HourBankTransaction.create(fakeDto());
  }

  public static HourBankTransactionDto fakeDto() {
    return new HourBankTransactionDto()
        .setTime(TimeFaker.fakeDto())
        .setDateTime(DateTimeFaker.fakeDto())
        .setReason(HourBankTransactionReasonFaker.fakeDto())
        .setOperation(HourBankTransactionOperationFaker.fakeDto());
  }

  public static Array<HourBankTransactionDto> fakeManyDto(int count) {
    Array<HourBankTransactionDto> fakeHourBankTransactionDtos = Array.createAsEmpty();
    for (var index = 0; index < count; index++) {
      fakeHourBankTransactionDtos.add(fakeDto());
    }
    return fakeHourBankTransactionDtos;
  }

}
