package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;
import br.com.chronos.core.auth.domain.events.AuthenticationRequestedEvent;
import br.com.chronos.core.auth.interfaces.AuthBroker;
import br.com.chronos.server.queue.rabbitmq.exchanges.CollaborationExchange;
import br.com.chronos.server.queue.rabbitmq.exchanges.NotificationExchange;

@Component
public class RabbitMqAuthBroker implements AuthBroker {
  private final RabbitTemplate rabbit;

  public RabbitMqAuthBroker(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Override
  public void publish(AccountUpdatedEvent event) {
    rabbit.convertAndSend(
        CollaborationExchange.NAME,
        AccountUpdatedEvent.NAME,
        event.getPayload());
  }

  @Override
  public void publish(AuthenticationRequestedEvent event) {
    rabbit.convertAndSend(
        NotificationExchange.NAME,
        AuthenticationRequestedEvent.NAME,
        event.getPayload());
  }
}
