package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public abstract class RabbitMqBroker {
  protected final RabbitTemplate rabbit;

  public RabbitMqBroker(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }
}
