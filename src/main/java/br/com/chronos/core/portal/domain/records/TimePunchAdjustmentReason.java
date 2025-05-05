package br.com.chronos.core.portal.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Text;

public record TimePunchAdjustmentReason(Reason value) {
  public enum Reason {
    UNWANTED,
    FORGOTTEN,
    SICK,
    OTHER
  }

  public static TimePunchAdjustmentReason create(String value) {
    var text = Text.create(value.toUpperCase(), "reason");
    try {
      return new TimePunchAdjustmentReason(Reason.valueOf(text.value()));
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser indesejado, esquecido, doente ou outro");
    }
  }
}
