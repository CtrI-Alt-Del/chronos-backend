package br.com.chronos.server.queue.jobs.work_schedule;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.events.DayOffScheduleSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.UpdateDaysOffScheduleUseCase;

@Component
public class UpdateDayOffScheduleJob {
  public static final String KEY = "work.schedule/update.day.off.schedule.job";

  @Autowired
  private DayOffSchedulesRepository repository;

  public void handle(DayOffScheduleSolicitationApprovedEvent.Payload payload) {
    var useCase = new UpdateDaysOffScheduleUseCase(repository);
    DayOffScheduleDto dayOffScheduleDto = new DayOffScheduleDto()
        .setDaysOff(payload.daysOff())
        .setDaysOffCount(payload.dayOffCount())
        .setWorkdaysCount(payload.workDayCount());
    Logger
        .getAnonymousLogger().info(payload.daysOff().toString() + " " + payload.dayOffCount() + " " + payload.workDayCount());
    useCase.execute(payload.collaboratorId(), dayOffScheduleDto);
  }
}
