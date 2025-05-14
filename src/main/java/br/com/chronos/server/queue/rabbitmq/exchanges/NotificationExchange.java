package br.com.chronos.server.queue.rabbitmq.exchanges;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.auth.domain.events.AuthenticationRequestedEvent;
import br.com.chronos.core.portal.domain.events.SolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.SolicitationCreatedEvent;
import br.com.chronos.core.portal.domain.events.SolicitationDeniedEvent;
import br.com.chronos.server.queue.jobs.notification.SendAuthenticationEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendSolicitationApprovedEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendSolicitationCreatedEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendSolicitationDeniedEmailJob;

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
  Queue sendSolicitationCreatedEmailJobQueue() {
    return new Queue(SendSolicitationCreatedEmailJob.KEY, true);
  }

  @Bean
  Queue sendSolicitationApprovedEmailJobQueue() {
    return new Queue(SendSolicitationApprovedEmailJob.KEY, true);
  }

  @Bean
  Queue sendSolicitationDeniedEmailJobQueue() {
    return new Queue(SendSolicitationDeniedEmailJob.KEY, true);
  }

  @Bean
  Binding sendAuthenticationEmailJobBinding() {
    return BindingBuilder
        .bind(sendAuthenticationEmailJobQueue())
        .to(notificationDirectExchange())
        .with(AuthenticationRequestedEvent.NAME);
  }

  @Bean
  Binding sendSolicitationCreatedEmailJobBinding() {
    return BindingBuilder
        .bind(sendSolicitationCreatedEmailJobQueue())
        .to(notificationDirectExchange())
        .with(SolicitationCreatedEvent.NAME);
  }

  @Bean
  Binding sendSolicitationApprovedEmailJobBinding() {
    return BindingBuilder
        .bind(sendSolicitationApprovedEmailJobQueue())
        .to(notificationDirectExchange())
        .with(SolicitationApprovedEvent.NAME);
  }

  @Bean
  Binding sendSolicitationDeniedEmailJobBinding() {
    return BindingBuilder
        .bind(sendSolicitationDeniedEmailJobQueue())
        .to(notificationDirectExchange())
        .with(SolicitationDeniedEvent.NAME);
  }
}
