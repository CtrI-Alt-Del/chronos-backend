package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.hour_bank.interfaces.HourBankBroker;

@Component
public class RabbitMqHourBankBroker implements HourBankBroker {
  private final RabbitTemplate rabbit;

  public RabbitMqHourBankBroker(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Override
  public void publish(HourBankTransactionCreatedEvent event) {
    rabbit.convertAndSend("work.schedule.exchange", HourBankTransactionCreatedEvent.NAME, event.getPayload());
  }
}
