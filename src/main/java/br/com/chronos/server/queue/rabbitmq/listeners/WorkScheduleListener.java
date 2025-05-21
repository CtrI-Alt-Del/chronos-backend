package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.WorkLeaveSolicitationApprovedEvent;
import br.com.chronos.server.queue.jobs.work_schedule.CreateWorkLeaveJob;
import br.com.chronos.server.queue.jobs.work_schedule.CreateWorkdayLogsJob;
import br.com.chronos.server.queue.jobs.work_schedule.ExcuseWorkdayAbsenceJob;
import br.com.chronos.server.queue.jobs.work_schedule.ScheduleDayOffJob;
import br.com.chronos.server.queue.jobs.work_schedule.UpdateWorkdayHourBankJob;

@Component
public class WorkScheduleListener {
  @Autowired
  private CreateWorkdayLogsJob createWorkdayLogsJob;

  @Autowired
  private CreateWorkLeaveJob createWorkLeaveJob;

  @Autowired
  private UpdateWorkdayHourBankJob updateWorkdayHourBankJob;

  @Autowired
  private ExcuseWorkdayAbsenceJob excuseWorkdayAbsenceJob;

  @Autowired
  private ScheduleDayOffJob scheduleDayOffJob;

  @RabbitListener(queues = CreateWorkdayLogsJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload CollaboratorsPreparedForWorkEvent.Payload payload) {
    createWorkdayLogsJob.handle(payload);
  }

  @RabbitListener(queues = UpdateWorkdayHourBankJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload HourBankTransactionCreatedEvent.Payload payload) {
    updateWorkdayHourBankJob.handle(payload);
  }

  @RabbitListener(queues = ExcuseWorkdayAbsenceJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload ExcusedAbsenceSolicitationApprovedEvent.Payload payload) {
    excuseWorkdayAbsenceJob.handle(payload);
  }

  @RabbitListener(queues = ScheduleDayOffJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload DayOffSolicitationApprovedEvent.Payload payload) {
    scheduleDayOffJob.handle(payload);
  }

  @RabbitListener(queues = CreateWorkLeaveJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload WorkLeaveSolicitationApprovedEvent.Payload payload) {
    createWorkLeaveJob.handle(payload);
  }
}
