package br.com.chronos.server.queue.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMqConfiguration {

  @Bean
  Queue queue() {
    return new Queue("solicitation-queue", true);
  }
}
