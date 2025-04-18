package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.work_schedule.domain.events.WorkdayDoneEvent;
import br.com.chronos.server.queue.jobs.hour_bank.CreateWorkdayHourBankTransactionJob;

@Component
public class HourBankListener {
  @Autowired
  CreateWorkdayHourBankTransactionJob createWorkdayHourBankTransactionJob;

  @RabbitListener(queues = WorkdayDoneEvent.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenToTimePunchClosedEvent(@Payload WorkdayDoneEvent.Payload payload) {
    createWorkdayHourBankTransactionJob.handle(payload);
  }
}
