package br.com.chronos.server.queue.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;
import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayCompletedEvent;

@Configuration
public class RabbitMqConfiguration {
  @Bean
  Queue workdayDoneEventEventQueue() {
    return new Queue(WorkdayCompletedEvent.KEY, true);
  }

  @Bean
  Queue collaboratorCreatedEventQueue() {
    return new Queue(CollaboratorCreatedEvent.KEY, true);
  }

  @Bean
  Queue accountUpdatedEventQueue() {
    return new Queue(AccountUpdatedEvent.KEY, true);
  }

  @Bean
  Queue collaboratorsPreparedForWorkEventQueue() {
    return new Queue(CollaboratorsPreparedForWorkEvent.KEY, true);
  }

  @Bean
  Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
