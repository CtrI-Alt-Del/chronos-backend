package br.com.chronos.core.solicitation.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Text;

public record SolicitationType(Type value) {
  public enum Type {
    EXCUSE_ABSENCE,
    TIME_PUNCH,
    DAY_OFF_SCHEDULE,
    DAY_OFF,
    PAID_OVERTIME
  }

  public static SolicitationType create(String value) {
    if (value == null) {
      throw new ValidationException("Tipo de solicitacao", "Nao pode ser nulo");
    }
    var text = Text.create(value.toUpperCase(), "tipo");
    try {
      return new SolicitationType(Type.valueOf(text.value()));
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser de ponto ou de jornada");
    }
  }
  public static SolicitationType createAsExcuseAbsence() {
    return new SolicitationType(Type.EXCUSE_ABSENCE);
  }

  public static SolicitationType createAsTimePunch() {
    return new SolicitationType(Type.TIME_PUNCH);
  }

  public static SolicitationType createAsDayOffSchedule() {
    return new SolicitationType(Type.DAY_OFF_SCHEDULE);
  }

  public static SolicitationType createAsDayOff() {
    return new SolicitationType(Type.DAY_OFF);
  }

  public Logical isTimePunch() {
    return Logical.create(value == Type.TIME_PUNCH);
  }
  public Logical isExcuseAbsence() {
    return Logical.create(value == Type.EXCUSE_ABSENCE);
  }

  public Logical isDayOffSchedule() {
    return Logical.create(value == Type.DAY_OFF_SCHEDULE);
  }

  public Logical isDayOff() {
    return Logical.create(value == Type.DAY_OFF);
  }

  public Logical isPaidOvertime() {
    return Logical.create(value == Type.PAID_OVERTIME);
  }
}
