package br.com.chronos.core.work_schedule.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.work_schedule.domain.dtos.WeekScheduleDto;

public final class WeekSchedule extends Entity {
  private Array<WeekdaySchedule> weekdaysSchedule;

  public WeekSchedule(WeekScheduleDto dto) {
    super(dto.id);
    weekdaysSchedule = Array.createFrom(dto.weekdays, WeekdaySchedule::new);

  }

}