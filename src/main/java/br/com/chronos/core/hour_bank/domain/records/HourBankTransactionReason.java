package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Text;

public record HourBankTransactionReason(ReasonName name) {
  public static enum ReasonName {
    OVERTIME,
    LATETIME,
    ADJUSTMENT,
    DAY_OFF
  }

  public static HourBankTransactionReason create(String reasonName) {
    var text = Text.create(reasonName.toUpperCase(), "Motivo da transação de banco de horas");
    try {
      var name = ReasonName.valueOf(text.value());
      return new HourBankTransactionReason(name);
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser hora extra, atraso ou ajuste");
    }
  }

  public static HourBankTransactionReason createAsAdjustment() {
    return HourBankTransactionReason.create("ADJUSTMENT");
  }

  public String toString() {
    return name.toString().toLowerCase();
  }
}
