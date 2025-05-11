package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.ScheduleDayOffUseCase;

@Component
public class ScheduleDayOffJob {
  public static final String KEY = "work.schedule/schedule.day.off.job";

  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  public void handle(DayOffSolicitationApprovedEvent.Payload payload) {
    System.out.println("OIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
    var useCase = new ScheduleDayOffUseCase(dayOffSchedulesRepository);
    useCase.execute(payload.dayOff(), payload.collaboratorId());
  }
}
