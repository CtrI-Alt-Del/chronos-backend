package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
import br.com.chronos.core.collaboration.domain.events.CollaboratorUpdatedEvent;
import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.collaboration.interfaces.CollaborationBroker;

@Component
public class RabbitMqCollaborationBroker implements CollaborationBroker {
  private final RabbitTemplate rabbit;

  public RabbitMqCollaborationBroker(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Override
  public void publish(CollaboratorCreatedEvent event) {
    rabbit.convertAndSend("", CollaboratorCreatedEvent.KEY, event.getPayload());
  }

  @Override
  public void publish(CollaboratorsPreparedForWorkEvent event) {
    rabbit.convertAndSend("", CollaboratorsPreparedForWorkEvent.KEY, event.getPayload());
  }

  @Override
  public void publish(CollaboratorUpdatedEvent event) {
    rabbit.convertAndSend("", CollaboratorUpdatedEvent.KEY, event.getPayload());
  }
}
