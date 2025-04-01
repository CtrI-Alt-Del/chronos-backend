package br.com.chronos.server.database.jpa.work_schedule.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WeekdayScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WeekdaySchedule;
import br.com.chronos.server.database.jpa.work_schedule.models.WeekdayScheduleModel;

@Component
public class WeekdayScheduleMapper {
  @Autowired
  private TimePunchMapper timePuchMapper;

  public WeekdayScheduleModel toModel(WeekdaySchedule entity) {
    return WeekdayScheduleModel
        .builder()
        .id(Id.random().value())
        .weekdayName(entity.getWeekday().name())
        .build();
  }

  public WeekdayScheduleDto toDto(WeekdayScheduleModel model) {
    return new WeekdayScheduleDto()
        .setId(model.getId().toString())
        .setWeekday(model.getWeekdayName().toString().toLowerCase())
        .setTimePunch(timePuchMapper.toDto(model.getTimePunch()));
  }

  public WeekdaySchedule toEntity(WeekdayScheduleModel model) {
    return new WeekdaySchedule(toDto(model));
  }
}
