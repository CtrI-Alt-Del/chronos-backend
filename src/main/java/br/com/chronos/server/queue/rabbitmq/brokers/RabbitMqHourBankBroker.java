package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.hour_bank.interfaces.HourBankBroker;
import br.com.chronos.server.queue.rabbitmq.exchanges.WorkScheduleExchange;

@Component
public class RabbitMqHourBankBroker extends RabbitMqBroker implements HourBankBroker {
  public RabbitMqHourBankBroker(RabbitTemplate rabbit) {
    super(rabbit);
  }

  @Override
  public void publish(HourBankTransactionCreatedEvent event) {
    rabbit.convertAndSend(
        WorkScheduleExchange.NAME,
        HourBankTransactionCreatedEvent.NAME,
        event.getPayload());
  }
}
