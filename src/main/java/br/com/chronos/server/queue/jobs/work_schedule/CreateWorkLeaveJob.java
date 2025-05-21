package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.events.WorkLeaveSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.domain.dtos.WorkLeaveDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkLeavesRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateWorkLeaveUseCase;

@Component
public class CreateWorkLeaveJob {
  public static final String KEY = "work.schedule/create.work.leaves.job";

  @Autowired
  private WorkLeavesRepository WorkLeavesRepository;

  public void handle(WorkLeaveSolicitationApprovedEvent.Payload payload) {
    var useCase = new CreateWorkLeaveUseCase(WorkLeavesRepository);
    var workLeaveDto = new WorkLeaveDto()
        .setStartedAt(payload.startedAt())
        .setEndedAt(payload.endedAt())
        .setIsVacation(payload.isVacation());
    useCase.execute(workLeaveDto, payload.collaboratorId());
  }
}
