package br.com.chronos.server.database.jpa.work_schedule.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.server.database.jpa.work_schedule.models.WorkScheduleModel;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;

@Component
public class WorkScheduleMapper {
  @Autowired
  private WeekdayScheduleMapper weekdayScheduleMapper;

  @Autowired
  private DayOffMapper dayOffMapper;

  public WorkScheduleModel toModel(WorkSchedule entity) {
    var model = WorkScheduleModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription().value())
        .daysOffCount(entity.getDaysOffCount().integer().value())
        .workDaysCount(entity.getWorkdaysCount().integer().value())
        .build();

    return model;
  }

  public WorkSchedule toEntity(WorkScheduleModel model) {
    var daysOff = Array.createFrom(model.getDaysOff(), dayOffMapper::toDto).list();
    var weekSchedule = Array.createFrom(model.getWeekdaySchedules(), weekdayScheduleMapper::toDto).list();

    var dto = new WorkScheduleDto()
        .setId(model.getId().toString())
        .setDescription(model.getDescription())
        .setWorkdaysCount(model.getWorkDaysCount())
        .setDaysOffCount(model.getDaysOffCount())
        .setWeekSchedule(weekSchedule)
        .setDaysOff(daysOff);

    return new WorkSchedule(dto);
  }

}
