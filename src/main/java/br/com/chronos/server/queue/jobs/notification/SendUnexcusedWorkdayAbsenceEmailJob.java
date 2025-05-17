package br.com.chronos.server.queue.jobs.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.notification.use_cases.SendUnexcusedWorkdayAbsenceEmailUseCase;
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceUnexcusedEvent;

@Component
public class SendUnexcusedWorkdayAbsenceEmailJob {
  public static final String KEY = "notification/send.unexcused.workday.absence.email.job";

  @Autowired
  private EmailProvider emailProvider;

  public void handle(WorkdayAbsenceUnexcusedEvent.Payload payload) {
    var useCase = new SendUnexcusedWorkdayAbsenceEmailUseCase(emailProvider);
    useCase.execute(payload.collaboratorEmail(), payload.collaboratorName(), payload.date());
  }
}
