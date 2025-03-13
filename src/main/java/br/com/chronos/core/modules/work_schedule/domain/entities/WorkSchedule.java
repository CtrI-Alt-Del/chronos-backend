package br.com.chronos.core.modules.work_schedule.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;

public final class WorkSchedule extends Entity {
  private String description;
  private Array<WeekdaySchedule> weekSchedule;
  private Array<Date> daysOff;

  public WorkSchedule(WorkScheduleDto dto) {
    super(dto.id);
    description = dto.description;
    weekSchedule = Array.createFrom(dto.weekSchedule, WeekdaySchedule::new);
    daysOff = Array.createFrom(dto.daysOff, Date::create);
  }

  public String getDescription() {
    return description;
  }

  public Array<WeekdaySchedule> getWeekSchedule() {
    return weekSchedule;
  }

  public Array<Date> getDaysOff() {
    return daysOff;
  }

  public WorkScheduleDto getDto() {
    return new WorkScheduleDto()
        .setId(getId().toString())
        .setDescription(getDescription())
        .setWeekSchedule(getWeekSchedule().map((weekdaySchedule) -> weekdaySchedule.getDto()).items())
        .setDaysOff(getDaysOff().map((dayOff) -> dayOff.value()).items());
  }

}
