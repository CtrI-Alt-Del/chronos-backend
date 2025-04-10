package br.com.chronos.server.queue.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import br.com.chronos.core.work_schedule.domain.events.TimePunchClosedEvent;

@Configuration
public class RabbitMqConfiguration {
  @Bean
  Queue timePunchClosedEventQueue() {
    return new Queue(TimePunchClosedEvent.KEY, true);
  }

  @Bean
  Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
