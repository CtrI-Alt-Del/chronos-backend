package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.events.AuthenticationRequestedEvent;
import br.com.chronos.core.portal.domain.events.SolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.SolicitationCreatedEvent;
import br.com.chronos.core.portal.domain.events.SolicitationDeniedEvent;
import br.com.chronos.server.queue.jobs.notification.SendAuthenticationEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendSolicitationApprovedEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendSolicitationCreatedEmailJob;
import br.com.chronos.server.queue.jobs.notification.SendSolicitationDeniedEmailJob;

@Component
public class NotificationListener {
  @Autowired
  private SendAuthenticationEmailJob sendAuthenticationEmailJob;

  @Autowired
  private SendSolicitationCreatedEmailJob sendSolicitationCreatedEmailJob;

  @Autowired
  private SendSolicitationApprovedEmailJob sendSolicitationApprovedEmailJob;

  @Autowired
  private SendSolicitationDeniedEmailJob sendSolicitationDeniedEmailJob;

  @RabbitListener(queues = SendAuthenticationEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload AuthenticationRequestedEvent.Payload payload) {
    sendAuthenticationEmailJob.handle(payload);
  }

  @RabbitListener(queues = SendSolicitationCreatedEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload SolicitationCreatedEvent.Payload payload) {
    System.out.println("SolicitationCreatedEvent.Payload: " + payload);
    sendSolicitationCreatedEmailJob.handle(payload);
  }

  @RabbitListener(queues = SendSolicitationApprovedEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload SolicitationApprovedEvent.Payload payload) {
    sendSolicitationApprovedEmailJob.handle(payload);
  }

  @RabbitListener(queues = SendSolicitationDeniedEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload SolicitationDeniedEvent.Payload payload) {
    sendSolicitationDeniedEmailJob.handle(payload);
  }
}
