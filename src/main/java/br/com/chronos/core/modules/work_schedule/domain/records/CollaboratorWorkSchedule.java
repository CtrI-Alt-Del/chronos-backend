package br.com.chronos.core.modules.work_schedule.domain.records;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;

public record CollaboratorWorkSchedule(Id collaboratorId, WorkSchedule workSchedule, TimePunch timePunchSchedule) {
  public static CollaboratorWorkSchedule create(
      String collaboratorId,
      WorkScheduleDto workScheduleDto,
      TimePunchDto timePunchScheduleDto) {
    return new CollaboratorWorkSchedule(
        Id.create(collaboratorId),
        new WorkSchedule(workScheduleDto),
        new TimePunch(timePunchScheduleDto));
  }
}
