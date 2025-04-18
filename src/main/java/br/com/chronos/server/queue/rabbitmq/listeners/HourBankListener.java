package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.work_schedule.domain.events.TimePunchClosedEvent;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForClosedTimePunchJob;

@Component
public class HourBankListener {
  @Autowired
  CreateHourBankTransactionForClosedTimePunchJob createHourBankTransactionForClosedTimePunchJob;

  @RabbitListener(queues = TimePunchClosedEvent.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenToTimePunchClosedEvent(@Payload TimePunchClosedEvent.Payload payload) {
    createHourBankTransactionForClosedTimePunchJob.handle(payload);
  }
}
