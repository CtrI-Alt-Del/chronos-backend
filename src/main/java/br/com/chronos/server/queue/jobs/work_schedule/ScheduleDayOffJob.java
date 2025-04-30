package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.solicitation.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.ShceduleDayOffUseCase;

@Component
public class ScheduleDayOffJob {
  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @Autowired
  private WorkScheduleBroker workScheduleBroker;

  public void handle(DayOffSolicitationApprovedEvent.Payload payload) {
    var useCase = new ShceduleDayOffUseCase(dayOffSchedulesRepository, workScheduleBroker);
    useCase.execute(payload.collaboratorId(), (byte) payload.collaboratorWorkload(), payload.dayOff());
  }
}
