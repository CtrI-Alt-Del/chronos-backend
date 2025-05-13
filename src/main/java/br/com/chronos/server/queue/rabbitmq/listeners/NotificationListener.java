package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.events.AuthenticationRequestedEvent;
import br.com.chronos.server.queue.jobs.notification.SendAuthenticationEmailJob;

@Component
public class NotificationListener {
  @Autowired
  private SendAuthenticationEmailJob sendAuthenticationEmailJob;

  @RabbitListener(queues = SendAuthenticationEmailJob.KEY, errorHandler = "rabbitMqErrorHandler")
  public void listenTo(@Payload AuthenticationRequestedEvent.Payload payload) {
    sendAuthenticationEmailJob.handle(payload);
  }
}
