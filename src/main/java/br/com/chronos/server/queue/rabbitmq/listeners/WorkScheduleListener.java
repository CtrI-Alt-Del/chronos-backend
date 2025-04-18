package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.server.queue.jobs.work_schedule.CreateWorkdayLogsJob;

@Component
public class WorkScheduleListener {
  @Autowired
  CreateWorkdayLogsJob createWorkdayLogsJob;

  @RabbitListener(queues = CollaboratorsPreparedForWorkEvent.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenToCollaboratorsPreparedForWorkEvent(
      @Payload CollaboratorsPreparedForWorkEvent.Payload payload) {
    createWorkdayLogsJob.handle(payload);
  }
}