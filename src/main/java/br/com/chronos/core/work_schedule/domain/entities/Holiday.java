package br.com.chronos.core.work_schedule.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.work_schedule.domain.dtos.HolidayDto;

public final class Holiday extends Entity {
  private Text name;
  private Date date;

  private Holiday(HolidayDto dto) {
    super(dto.id);
    name = Text.create(dto.name, "Holiday name");
    date = Date.create(dto.date);
  }

  public Text getName() {
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
