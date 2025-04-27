package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.solicitation.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.server.queue.jobs.work_schedule.CreateWorkdayLogsJob;
import br.com.chronos.server.queue.jobs.work_schedule.ExcuseWorkdayAbsenceJob;
import br.com.chronos.server.queue.jobs.work_schedule.UpdateWorkdayHourBankJob;

@Component
public class WorkScheduleListener {
  @Autowired
  private CreateWorkdayLogsJob createWorkdayLogsJob;

  @Autowired
  private UpdateWorkdayHourBankJob updateWorkdayHourBankJob;

  @Autowired
  private ExcuseWorkdayAbsenceJob excuseWorkdayAbsenceJob;

  @RabbitListener(queues = CollaboratorsPreparedForWorkEvent.NAME, errorHandler = "rabbitMqErrorHandler")
  public void listenToCollaboratorsPreparedForWorkEvent(
      @Payload CollaboratorsPreparedForWorkEvent.Payload payload) {
    createWorkdayLogsJob.handle(payload);
  }

  @RabbitListener(queues = HourBankTransactionCreatedEvent.NAME, errorHandler = "rabbitMqErrorHandler")
  public void listenToHourBankTransactionCreatedEvent(
      @Payload HourBankTransactionCreatedEvent.Payload payload) {
    updateWorkdayHourBankJob.handle(payload);
  }

  @RabbitListener(queues = ExcusedAbsenceSolicitationApprovedEvent.NAME, errorHandler = "rabbitMqErrorHandler")
  public void listenToExcusedAbsenceSolicitationApprovedEvent(
      @Payload ExcusedAbsenceSolicitationApprovedEvent.Payload payload) {
    excuseWorkdayAbsenceJob.handle(payload);
  }
}