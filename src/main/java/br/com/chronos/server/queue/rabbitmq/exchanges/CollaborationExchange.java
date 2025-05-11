package br.com.chronos.server.queue.rabbitmq.exchanges;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;
import br.com.chronos.server.queue.jobs.collaboration.UpdateCollaboratorJob;

@Configuration
public class CollaborationExchange {
  public static final String NAME = "collaboration.direct.exchange";

  @Bean
  DirectExchange collaborationDirectExchange() {
    return new DirectExchange(NAME, true, false);
  }

  @Bean
  Queue updateCollaboratorJobQueue() {
    return new Queue(UpdateCollaboratorJob.KEY, true);
  }

  @Bean
  Binding updateCollaboratorJobBinding() {
    return BindingBuilder
        .bind(updateCollaboratorJobQueue())
        .to(collaborationDirectExchange())
        .with(AccountUpdatedEvent.NAME);
  }
}
