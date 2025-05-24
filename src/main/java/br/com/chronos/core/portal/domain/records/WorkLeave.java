package br.com.chronos.core.portal.domain.records;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveDto;
import br.com.chronos.core.portal.domain.entities.Justification;

public record WorkLeave(
    Array<Date> dates,
    Logical isVacation,
    Justification justification) {
  public static WorkLeave create(WorkLeaveDto dto) {
    return new WorkLeave(
        Array.createFrom(dto.dates, Date::create),
        Logical.create(dto.isVacation),
        new Justification(dto.justification));
  }

  public WorkLeaveDto toDto() {
    return new WorkLeaveDto()
        .setDates(dates.map(date -> date.value()).list())
        .setIsVacation(isVacation.value())
        .setJustification(justification.getDto());
  }
}
