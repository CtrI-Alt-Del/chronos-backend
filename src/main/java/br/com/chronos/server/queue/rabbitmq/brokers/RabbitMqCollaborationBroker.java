package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
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
}
