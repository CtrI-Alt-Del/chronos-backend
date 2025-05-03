package br.com.chronos.server.queue.rabbitmq.exchanges;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
import br.com.chronos.server.queue.jobs.auth.CreateAccountJob;

@Configuration
public class AuthExchange {
  @Bean
  DirectExchange authDirectExchange() {
    return new DirectExchange("auth.direct.exchange", true, false);
  }

  @Bean
  Queue createAccountJobQueue() {
    return new Queue(CreateAccountJob.KEY, true);
  }

  @Bean
  Binding createAccountJobBinding() {
    return BindingBuilder
        .bind(createAccountJobQueue())
        .to(authDirectExchange())
        .with(CollaboratorCreatedEvent.NAME);
  }
}
