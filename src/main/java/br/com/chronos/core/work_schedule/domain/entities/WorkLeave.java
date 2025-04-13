package br.com.chronos.core.work_schedule.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.work_schedule.domain.dtos.WorkLeaveDto;

public final class WorkLeave extends Entity {
  private Date startedAt;
  private Date endedAt;

  public WorkLeave(WorkLeaveDto dto) {
    super(dto.id);
    startedAt = Date.create(dto.startedAt);
    endedAt = Date.create(dto.endedAt);
  }

  public Logical covers(Date date) {
    return startedAt.isEqualOrAfter(date).or(endedAt.isEqualOrBefore(date));
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
