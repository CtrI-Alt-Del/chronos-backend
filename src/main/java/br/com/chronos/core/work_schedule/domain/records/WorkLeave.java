package br.com.chronos.core.work_schedule.domain.records;

import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.work_schedule.domain.dtos.WorkLeaveDto;

public record WorkLeave(DateRange dateRange, Logical isVacation) {
  public static WorkLeave create(WorkLeaveDto dto) {
    return new WorkLeave(
        DateRange.create(dto.startedAt, dto.endedAt),
        Logical.create(dto.isVacation));
  }

  public WorkdayStatus getWorkdayStatus() {
    if (isVacation.isTrue()) {
      return WorkdayStatus.createAsVacation();
    }
    return WorkdayStatus.createAsWithdraw();
  }
}
