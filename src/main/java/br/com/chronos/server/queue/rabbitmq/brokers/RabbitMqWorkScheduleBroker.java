package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceExcusedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceUnexcusedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayClosedEvent;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.server.queue.rabbitmq.exchanges.HourBankExchange;
import br.com.chronos.server.queue.rabbitmq.exchanges.NotificationExchange;

@Component
public class RabbitMqWorkScheduleBroker extends RabbitMqBroker implements WorkScheduleBroker {
  public RabbitMqWorkScheduleBroker(RabbitTemplate rabbit) {
    super(rabbit);
  }

  @Override
  public void publish(WorkdayClosedEvent event) {
    rabbit.convertAndSend(HourBankExchange.NAME, WorkdayClosedEvent.NAME, event.getPayload());
  }

  @Override
  public void publish(WorkdayAbsenceExcusedEvent event) {
    rabbit.convertAndSend(HourBankExchange.NAME, WorkdayAbsenceExcusedEvent.NAME, event.getPayload());
  }

  @Override
  public void publish(WorkdayAbsenceUnexcusedEvent event) {
    rabbit.convertAndSend(NotificationExchange.NAME, WorkdayAbsenceUnexcusedEvent.NAME, event.getPayload());
  }
}
