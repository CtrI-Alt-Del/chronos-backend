package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;
import br.com.chronos.core.auth.interfaces.AuthBroker;

@Component
public class RabbitMqAuthBroker implements AuthBroker {
  private final RabbitTemplate rabbit;

  public RabbitMqAuthBroker(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Override
  public void publish(AccountUpdatedEvent event) {
    rabbit.convertAndSend("", AccountUpdatedEvent.KEY, event.getPayload());
  }
}
