package br.com.chronos.core.modules.work_schedule.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkLeaveDto;

public final class WorkLeave extends Entity {
  private Date startedAt;
  private Date endedAt;

  public WorkLeave(WorkLeaveDto dto) {
    super(dto.id);
    startedAt = Date.create(dto.startedAt);
    endedAt = Date.create(dto.endedAt);
  }

  public boolean covers(Date date) {
    return startedAt.isEqualOrAfter(date) || endedAt.isEqualOrBefore(date);
  }

  public Date getStartedAt() {
    return startedAt;
  }

  public Date getEndedAt() {
    return endedAt;
  }

  public WorkLeaveDto getDto() {
    return new WorkLeaveDto()
        .setId(getId().toString())
        .setStartedAt(getStartedAt().value())
        .setEndedAt(getEndedAt().value());
  }

}
