package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.solicitation.domain.events.PaidOvertimeSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.interfaces.PortalBroker;

@Component
public class RabbitMqPortalBroker implements PortalBroker {
  private final RabbitTemplate rabbit;

  public RabbitMqPortalBroker(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Override
  public void publish(PaidOvertimeSolicitationApprovedEvent event) {
    rabbit.convertAndSend("", HourBankTransactionCreatedEvent.NAME, event.getPayload());
  }

}
