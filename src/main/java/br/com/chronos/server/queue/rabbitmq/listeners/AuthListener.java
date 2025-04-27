package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;
import br.com.chronos.server.queue.jobs.collaboration.UpdateCollaboratorJob;

@Component
public class AuthListener {
  @Autowired
  private UpdateCollaboratorJob updateCollaboratorJob;

  @RabbitListener(queues = AccountUpdatedEvent.NAME, errorHandler = "rabbitMqErrorHandler")
  public void listenToCollaboratorUpdated(@Payload AccountUpdatedEvent.Payload payload) {
    updateCollaboratorJob.handle(payload);
  }
}
