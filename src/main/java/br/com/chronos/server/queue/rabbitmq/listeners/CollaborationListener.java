package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
import br.com.chronos.core.collaboration.domain.events.CollaboratorUpdatedEvent;
import br.com.chronos.server.queue.jobs.auth.CreateAccountJob;
import br.com.chronos.server.queue.jobs.auth.UpdateAccountJob;

@Component
public class CollaborationListener {
  @Autowired
  CreateAccountJob createAccountJob;
  @Autowired
  UpdateAccountJob updateAccountJob;

  @RabbitListener(queues = CollaboratorCreatedEvent.KEY)
  public void listenToCollaboratorCreated(@Payload CollaboratorCreatedEvent.Payload payload) {
    createAccountJob.handle(payload);
  }
  @RabbitListener(queues = CollaboratorUpdatedEvent.KEY)
  public void listenToCollaboratorUpdated(@Payload CollaboratorUpdatedEvent.Payload payload) {
    updateAccountJob.handle(payload);
  }
}
