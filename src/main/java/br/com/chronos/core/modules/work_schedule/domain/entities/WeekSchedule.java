package br.com.chronos.core.modules.work_schedule.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Array;

public final class WeekSchedule extends Entity {
  private Array<WeekdaySchedule> weekdaysSchedule;

}