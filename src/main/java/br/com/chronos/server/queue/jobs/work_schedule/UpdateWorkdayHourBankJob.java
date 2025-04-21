package br.com.chronos.server.queue.jobs.work_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.UpdateWorkdayLogHourBankUseCase;

@Component
public class UpdateWorkdayHourBankJob {
  @Autowired
  private WorkdayLogsRepository repository;

  public void handle(HourBankTransactionCreatedEvent.Payload payload) {
    var useCase = new UpdateWorkdayLogHourBankUseCase(repository);
    useCase.execute(
        payload.collaboratorId(),
        payload.date(),
        payload.time(),
        payload.isCreditOperation());
  }
}
