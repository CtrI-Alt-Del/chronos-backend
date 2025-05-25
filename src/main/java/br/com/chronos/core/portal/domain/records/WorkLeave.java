package br.com.chronos.core.portal.domain.records;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveDto;
import br.com.chronos.core.portal.domain.entities.Justification;

public record WorkLeave(
    Date startedAt,
    Date endedAt,
    Logical isVacation,
    Justification justification) {
  public static WorkLeave create(WorkLeaveDto dto) {
    return new WorkLeave(
        Date.create(dto.startedAt),
        Date.create(dto.endedAt),
        Logical.create(dto.isVacation),
        new Justification(dto.justification));
  }

  public WorkLeaveDto toDto() {
    return new WorkLeaveDto()
        .setStartedAt(startedAt.value())
        .setEndedAt(endedAt.value())
        .setIsVacation(isVacation.value())
        .setJustification(justification.getDto());
  }
}
