package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
import br.com.chronos.server.queue.jobs.auth.CreateAccountJob;

@Component
public class CollaborationListener {
  @Autowired
  CreateAccountJob createAccountJob;

  @RabbitListener(queues = CollaboratorCreatedEvent.NAME, errorHandler = "rabbitMqErrorHandler")
  public void listenToCollaboratorCreated(@Payload CollaboratorCreatedEvent.Payload payload) {
    createAccountJob.handle(payload);
  }
}
