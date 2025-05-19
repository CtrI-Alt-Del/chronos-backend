package br.com.chronos.core.portal.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Text;

public record SolicitationType(Type value) {
  public enum Type {
    WITHDRAW,
    EXCUSED_ABSENCE,
    TIME_PUNCH_ADJUSMENT,
    DAY_OFF_SCHEDULE,
    DAY_OFF,
    PAID_OVERTIME,
    VACATION,
    WORK_LEAVE,
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

  public static SolicitationType createAsExcusedAbsence() {
    return new SolicitationType(Type.EXCUSED_ABSENCE);
  }

  public static SolicitationType createAsWithdraw() {
    return new SolicitationType(Type.WITHDRAW);
  }

  public static SolicitationType createAsTimePunchAdjusment() {
    return new SolicitationType(Type.TIME_PUNCH_ADJUSMENT);
  }

  public static SolicitationType createAsDayOffSchedule() {
    return new SolicitationType(Type.DAY_OFF_SCHEDULE);
  }

  public static SolicitationType createAsDayOff() {
    return new SolicitationType(Type.DAY_OFF);
  }

  public static SolicitationType createPaidOvertime() {
    return new SolicitationType(Type.PAID_OVERTIME);
  }

  public static SolicitationType createAsVacation() {
    return new SolicitationType(Type.VACATION);
  }

  public static SolicitationType createAsWorkLeave() {
    return new SolicitationType(Type.WORK_LEAVE);
  }

  public Logical isVacation() {
    return Logical.create(value == Type.VACATION);
  }

  public Logical isWithdraw() {
    return Logical.create(value == Type.WITHDRAW);
  }

  public Logical isTimePunch() {
    return Logical.create(value == Type.TIME_PUNCH_ADJUSMENT);
  }

  public Logical isExcusedAbsence() {
    return Logical.create(value == Type.EXCUSED_ABSENCE);
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

  public String toString() {
    return value.toString().toLowerCase();
  }

}
