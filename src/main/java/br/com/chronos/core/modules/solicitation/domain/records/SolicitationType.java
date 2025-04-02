package br.com.chronos.core.modules.solicitation.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Text;

public record SolicitationType(Type value){
 public enum Type{
    TIME_PUNCH,
    WORK_SCHEDULE
  } 
 public static SolicitationType create(String value) {
    if (value == null) {
      throw new ValidationException("Tipo de solicitacao","Nao pode ser nulo");
    }
    var text = Text.create(value.toUpperCase(), "tipo");
    try {
      return new SolicitationType(Type.valueOf(text.value()));
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser de ponto ou de jornada");
    }
  }
  public static SolicitationType createAsTimePunch(){
    return new SolicitationType(Type.TIME_PUNCH);
  }
  public static SolicitationType createAsWorkSchedule(){
    return new SolicitationType(Type.WORK_SCHEDULE);
  }
}
