package br.com.chronos.server.database.jpa.work_schedule.mappers;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffModel;

@Component
public class DayOffMapper {
  public DayOffModel toModel(Date dayOff) {
    return DayOffModel
        .builder()
        .id(Id.random().value())
        .date(dayOff.value())
        .build();
  }

  public Date toRecord(DayOffModel dayOff) {
    return Date.create(dayOff.getDate());
  }

  public LocalDate toDto(DayOffModel model) {
    return model.getDate();
  }
}
