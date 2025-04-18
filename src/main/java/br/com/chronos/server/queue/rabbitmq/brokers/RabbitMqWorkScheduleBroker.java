package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.work_schedule.domain.events.WorkdayCompletedEvent;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;

@Component
public class RabbitMqWorkScheduleBroker implements WorkScheduleBroker {
  private final RabbitTemplate rabbit;

  public RabbitMqWorkScheduleBroker(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  public void publish(WorkdayCompletedEvent event) {
    rabbit.convertAndSend("", WorkdayCompletedEvent.KEY, event.getPayload());
  }
}
