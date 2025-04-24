package br.com.chronos.server.queue.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import br.com.chronos.core.global.domain.exceptions.AppException;

@Component
public class RabbitMqErrorHandler implements RabbitListenerErrorHandler {

  @Override
  public Object handleError(
      Message amqpMessage,
      Channel channel,
      org.springframework.messaging.Message<?> message,
      ListenerExecutionFailedException exception) throws Exception {
    var cause = exception.getCause();
    if (cause instanceof AppException) {
      var appException = (AppException) cause;
      System.out.println("AppException title: " + appException.getTitle());
      System.out.println("AppException message: " + appException.getMessage());
    }

    return null;
  }
}