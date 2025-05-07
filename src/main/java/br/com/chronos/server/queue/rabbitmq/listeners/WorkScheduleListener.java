package br.com.chronos.server.queue.rabbitmq.listeners;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.portal.domain.events.DayOffScheduleSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.server.queue.jobs.work_schedule.CreateWorkdayLogsJob;
import br.com.chronos.server.queue.jobs.work_schedule.ExcuseWorkdayAbsenceJob;
import br.com.chronos.server.queue.jobs.work_schedule.UpdateDayOffScheduleJob;
import br.com.chronos.server.queue.jobs.work_schedule.UpdateWorkdayHourBankJob;

@Component
public class WorkScheduleListener {
  @Autowired
  private CreateWorkdayLogsJob createWorkdayLogsJob;

  @Autowired
  private UpdateWorkdayHourBankJob updateWorkdayHourBankJob;

  @Autowired
  private ExcuseWorkdayAbsenceJob excuseWorkdayAbsenceJob;

  @Autowired
  private UpdateDayOffScheduleJob updateDayOffScheduleJob;

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

  @RabbitListener(queues = UpdateDayOffScheduleJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload DayOffScheduleSolicitationApprovedEvent.Payload payload) {
    updateDayOffScheduleJob.handle(payload);
  }
  // @RabbitListener(queues =
  // DayOffScheduleSolicitationApprovedEvent.NAME,errorHandler =
  // "rabbitMqErrorHandler")
  // public void listenTo(@Payload DayOffScheduleSolicitationApprovedEvent.Payload
  // payload){
  // updateDayOffScheduleJob.handle(payload);
  // }
}
