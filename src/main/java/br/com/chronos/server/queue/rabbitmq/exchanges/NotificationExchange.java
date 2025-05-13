package br.com.chronos.server.queue.rabbitmq.exchanges;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.auth.domain.events.AuthenticationRequestedEvent;
import br.com.chronos.server.queue.jobs.notification.SendAuthenticationEmailJob;

@Configuration
public class NotificationExchange {
  public static final String NAME = "notification.direct.exchange";

  @Bean
  DirectExchange notificationDirectExchange() {
    return new DirectExchange(NAME, true, false);
  }

  @Bean
  Queue sendAuthenticationEmailJobQueue() {
    return new Queue(SendAuthenticationEmailJob.KEY, true);
  }

  @Bean
  Binding sendAuthenticationEmailJobBinding() {
    return BindingBuilder
        .bind(sendAuthenticationEmailJobQueue())
        .to(notificationDirectExchange())
        .with(AuthenticationRequestedEvent.NAME);
  }
}
