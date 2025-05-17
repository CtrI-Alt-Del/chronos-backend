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
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceUnexcusedEvent;
import br.com.chronos.server.queue.jobs.notification.SendAuthenticationEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendSolicitationApprovalEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendSolicitationCreationEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendSolicitationDenialEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendUnexcusedWorkdayAbsenceEmailJob;

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
  Queue sendUnexcusedWorkdayAbsenceEmailJobQueue() {
    return new Queue(SendUnexcusedWorkdayAbsenceEmailJob.KEY, true);
  }

  @Bean
  Queue sendSolicitationCreationEmailJobQueue() {
    return new Queue(SendSolicitationCreationEmailJob.KEY, true);
  }

  @Bean
  Queue sendSolicitationApprovalEmailJobQueue() {
    return new Queue(SendSolicitationApprovalEmailJob.KEY, true);
  }

  @Bean
  Queue sendSolicitationDenialEmailJobQueue() {
    return new Queue(SendSolicitationDenialEmailJob.KEY, true);
  }

  @Bean
  Binding sendAuthenticationEmailJobBinding() {
    return BindingBuilder
        .bind(sendAuthenticationEmailJobQueue())
        .to(notificationDirectExchange())
        .with(AuthenticationRequestedEvent.NAME);
  }

  @Bean
  Binding sendSolicitationCreationEmailJobBinding() {
    return BindingBuilder
        .bind(sendSolicitationCreationEmailJobQueue())
        .to(notificationDirectExchange())
        .with(SolicitationCreatedEvent.NAME);
  }

  @Bean
  Binding sendSolicitationApprovalEmailJobBinding() {
    return BindingBuilder
        .bind(sendSolicitationApprovalEmailJobQueue())
        .to(notificationDirectExchange())
        .with(SolicitationApprovedEvent.NAME);
  }

  @Bean
  Binding sendSolicitationDenialEmailJobBinding() {
    return BindingBuilder
        .bind(sendSolicitationDenialEmailJobQueue())
        .to(notificationDirectExchange())
        .with(SolicitationDeniedEvent.NAME);
  }

  @Bean
  Binding sendUnexcusedWorkdayAbsenceEmailJobBinding() {
    return BindingBuilder
        .bind(sendUnexcusedWorkdayAbsenceEmailJobQueue())
        .to(notificationDirectExchange())
        .with(WorkdayAbsenceUnexcusedEvent.NAME);
  }
}
