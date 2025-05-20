package br.com.chronos.server.database.jpa.work_schedule.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.work_schedule.domain.dtos.WorkLeaveDto;
import br.com.chronos.core.work_schedule.domain.records.WorkLeave;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkLeaveModel;

@Component
public class WorkLeaveMapper {
  public WorkLeaveModel toModel(WorkLeave workLeave) {
    return WorkLeaveModel
        .builder()
        .startedAt(workLeave.dateRange().startDate().value())
        .endedAt(workLeave.dateRange().endDate().value())
        .build();
  }

  public WorkLeave toEntity(WorkLeaveModel workLeaveModel) {
    var dto = new WorkLeaveDto()
        .setStartedAt(workLeaveModel.getStartedAt())
        .setEndedAt(workLeaveModel.getEndedAt())
        .setIsVacation(workLeaveModel.isVacation());
    return WorkLeave.create(dto);
  }
}
