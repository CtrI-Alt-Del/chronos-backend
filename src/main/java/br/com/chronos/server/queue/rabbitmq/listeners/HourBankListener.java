package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceExcusedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayClosedEvent;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForDayOffJob;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForExcusedAbsenceJob;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForWorkdayJob;

@Component
public class HourBankListener {
  @Autowired
  private CreateHourBankTransactionForWorkdayJob createHourBankTransactionForWorkdayJob;

  @Autowired
  CreateHourBankTransactionForDayOffJob createHourBankTransactionForDayOffJob;

  @Autowired
  CreateHourBankTransactionForExcusedAbsenceJob createHourBankTransactionForExcusedAbsenceJob;

  @RabbitListener(queues = CreateHourBankTransactionForWorkdayJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload WorkdayClosedEvent.Payload payload) {
    createHourBankTransactionForWorkdayJob.handle(payload);
  }

  @RabbitListener(queues = CreateHourBankTransactionForDayOffJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload DayOffSolicitationApprovedEvent.Payload payload) {
    createHourBankTransactionForDayOffJob.handle(payload);
  }

  @RabbitListener(queues = CreateHourBankTransactionForWorkdayJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload WorkdayAbsenceExcusedEvent.Payload payload) {
    createHourBankTransactionForExcusedAbsenceJob.handle(payload);
  }
}
