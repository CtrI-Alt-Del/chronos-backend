package br.com.chronos.server.database.jpa.work_schedule.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.server.database.jpa.work_schedule.models.TimePunchModel;

@Component
public class TimePunchMapper {
  public TimePunchModel toModel(TimePunch entity) {
    var model = TimePunchModel.builder()
        .id(entity.getId().value())
        .firstClockIn(entity.getFirstClockIn().value())
        .firstClockOut(entity.getSecondClockOut().value())
        .seconcClockIn(entity.getSecondClockIn().value())
        .secondClockOut(entity.getSecondClockOut().value())
        .build();

    return model;
  }

  public TimePunch toEntity(TimePunchModel model) {
    var dto = new TimePunchDto()
        .setId(model.getId().toString())
        .setFirstClockOut(model.getFirstClockIn())
        .setFirstClockOut(model.getFirstClockOut())
        .setSecondClockIn(model.getSeconcClockIn())
        .setSecondClockOut(model.getSecondClockOut());

    return new TimePunch(dto);
  }
}
