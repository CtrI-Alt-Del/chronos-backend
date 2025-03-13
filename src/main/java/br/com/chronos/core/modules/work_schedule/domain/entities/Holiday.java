package br.com.chronos.core.modules.work_schedule.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.work_schedule.domain.dtos.HolidayDto;

public final class Holiday extends Entity {
  private String name;
  private Date date;

  private Holiday(HolidayDto dto) {
    super(dto.id);
    name = dto.name;
    date = Date.create(dto.date);
  }

  public String getName() {
    return name;
  }

  public Date getDate() {
    return date;
  }

  public HolidayDto getDto() {
    return new HolidayDto()
        .setId(getId().toString())
        .setName(getName().toString())
        .setDate(getDate().value());
  }

}
