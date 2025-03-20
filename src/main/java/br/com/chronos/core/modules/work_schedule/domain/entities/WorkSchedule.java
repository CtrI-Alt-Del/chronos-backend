package br.com.chronos.core.modules.work_schedule.domain.entities;

import br.com.chronos.core.modules.global.domain.abstracts.Entity;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Count;
import br.com.chronos.core.modules.global.domain.records.Text;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.ZeroDaysOffCountException;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.ZeroWorkdaysCountException;
import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus;
import br.com.chronos.core.modules.work_schedule.domain.records.DaysOffSchedule;

public final class WorkSchedule extends Entity {
  private Text description;
  private Count workdaysCount;
  private Count daysOffCount;
  private Array<WeekdaySchedule> weekSchedule;
  private DaysOffSchedule daysOffSchedule;

  public WorkSchedule(WorkScheduleDto dto) {
    super(dto.id);

    if (dto.workdaysCount == 0) {
      throw new ZeroWorkdaysCountException();
    }
    if (dto.daysOffCount == 0) {
      throw new ZeroDaysOffCountException();
    }

    description = Text.create(dto.description, "descrição da escala de trabalho");
    workdaysCount = Count.create(dto.workdaysCount, "Contagem de dias de trabalho");
    daysOffCount = Count.create(dto.daysOffCount, "Contagem de folgas");
    weekSchedule = Array.createFrom(dto.weekSchedule, WeekdaySchedule::new);
    daysOffSchedule = DaysOffSchedule.create(dto.daysOff);
  }

  public void resetDaysOff() {
    daysOffSchedule = DaysOffSchedule.create(
        workdaysCount.integer().value(),
        daysOffCount.integer().value());

  }

  public WorkdayStatus getWorkdayStatus() {
    if (daysOffSchedule.isTodayDayOff().isTrue()) {
      return WorkdayStatus.createAsDayOff();
    }
    return WorkdayStatus.createAsNormalDay();
  }

  public Text getDescription() {
    return description;
  }

  public Count getWorkdaysCount() {
    return workdaysCount;
  }

  public Count getDaysOffCount() {
    return daysOffCount;
  }

  public Array<WeekdaySchedule> getWeekSchedule() {
    return weekSchedule;
  }

  public DaysOffSchedule getDaysOffSchedule() {
    return daysOffSchedule;
  }

  public WorkScheduleDto getDto() {
    return new WorkScheduleDto()
        .setId(getId().toString())
        .setDescription(getDescription().value())
        .setWeekSchedule(getWeekSchedule()
            .map((weekdaySchedule) -> weekdaySchedule.getDto()).list())
        .setDaysOff(getDaysOffSchedule().days().map((dayOff) -> dayOff.value()).list());
  }

}
