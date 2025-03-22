package br.com.chronos.server.database.jpa.work_schedule.mappers;

import br.com.chronos.server.database.jpa.work_schedule.models.WorkScheduleModel;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;

@Component
public class WorkScheduleMapper {
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
    List<LocalDate> daysOff = Array.createFrom(
        model.getDaysOff(), (dayOffModel) -> dayOffModel.getDate()).list();

    var dto = new WorkScheduleDto()
        .setId(model.getId().toString())
        .setDescription(model.getDescription())
        .setWorkdaysCount(model.getWorkDaysCount())
        .setDaysOffCount(model.getDaysOffCount())
        .setDaysOff(daysOff);

    return new WorkSchedule(dto);
  }
}
