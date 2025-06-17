package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

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

@Component
public class NotificationListener {
  @Autowired
  private SendAuthenticationEmailJob sendAuthenticationEmailJob;

  @Autowired
  private SendSolicitationCreationEmailJob sendSolicitationCreationEmailJob;

  @Autowired
  private SendSolicitationApprovalEmailJob sendSolicitationApprovalEmailJob;

  @Autowired
  private SendSolicitationDenialEmailJob sendSolicitationDenialEmailJob;

  @Autowired
  private SendUnexcusedWorkdayAbsenceEmailJob sendUnexcusedWorkdayAbsenceEmailJob;

  @RabbitListener(queues = SendAuthenticationEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload AuthenticationRequestedEvent.Payload payload) {
    sendAuthenticationEmailJob.handle(payload);
  }

  @RabbitListener(queues = SendSolicitationCreationEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload SolicitationCreatedEvent.Payload payload) {
    System.out.println("Listener: ");
    sendSolicitationCreationEmailJob.handle(payload);
  }

  @RabbitListener(queues = SendSolicitationApprovalEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload SolicitationApprovedEvent.Payload payload) {
    sendSolicitationApprovalEmailJob.handle(payload);
  }

  @RabbitListener(queues = SendSolicitationDenialEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload SolicitationDeniedEvent.Payload payload) {
    sendSolicitationDenialEmailJob.handle(payload);
  }

  @RabbitListener(queues = SendUnexcusedWorkdayAbsenceEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload WorkdayAbsenceUnexcusedEvent.Payload payload) {
    sendUnexcusedWorkdayAbsenceEmailJob.handle(payload);
  }
}
