package br.com.chronos.server.database.jpa.work_schedule.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffScheduleModel;

@Component
public class DayOffScheduleMapper {
  @Autowired
  private DayOffMapper dayOffMapper;

  public DayOffScheduleModel toModel(DayOffSchedule entity) {
    var model = DayOffScheduleModel.builder()
        .id(entity.getId().value())
        .daysOffCount(entity.getDaysOffCount().integer().value())
        .workDaysCount(entity.getWorkdaysCount().integer().value())
        .build();

    return model;
  }

  public DayOffScheduleDto toDto(DayOffScheduleModel model) {
    var daysOff = Array.createFrom(model.getDaysOff(), dayOffMapper::toDto).list();
    var dto = new DayOffScheduleDto()
        .setId(model.getId().toString())
        .setWorkdaysCount(model.getWorkDaysCount())
        .setDaysOffCount(model.getDaysOffCount())
        .setDaysOff(daysOff);

    return dto;
  }

  public DayOffSchedule toEntity(DayOffScheduleModel model) {
    return new DayOffSchedule(toDto(model));
  }
}
