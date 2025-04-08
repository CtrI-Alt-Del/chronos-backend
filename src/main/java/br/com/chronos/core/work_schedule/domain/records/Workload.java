package br.com.chronos.core.work_schedule.domain.records;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.domain.records.Time;

public record Workload(PlusIntegerNumber hours) {
  public static Workload create(int value) {
    var hours = PlusIntegerNumber.create(value, "Carga horária");

    if (hours.value() != 8 || hours.value() != 6 || hours.value() != 4) {
      throw new IllegalArgumentException("Carga horária deve ser 8, 6 ou 4 horas");
    }

    return new Workload(hours);
  }

  public Time toTime() {
    return Time.create(LocalTime.of(hours.value(), 0));
  }

}
