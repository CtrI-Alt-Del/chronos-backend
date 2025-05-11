package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.events.DayOffScheduleSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.PaidOvertimeSolicitationApprovedEvent;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.server.queue.rabbitmq.exchanges.HourBankExchange;
import br.com.chronos.server.queue.rabbitmq.exchanges.WorkScheduleExchange;

@Component
public class RabbitMqPortalBroker implements PortalBroker {
  private final RabbitTemplate rabbit;

  public RabbitMqPortalBroker(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Override
  public void publish(ExcusedAbsenceSolicitationApprovedEvent event) {
    rabbit.convertAndSend(
        WorkScheduleExchange.NAME,
        ExcusedAbsenceSolicitationApprovedEvent.NAME,
        event.getPayload());
    rabbit.convertAndSend(
        HourBankExchange.NAME,
        ExcusedAbsenceSolicitationApprovedEvent.NAME,
        event.getPayload());
  }

  @Override
  public void publish(DayOffSolicitationApprovedEvent event) {
    rabbit.convertAndSend(
        WorkScheduleExchange.NAME,
        DayOffSolicitationApprovedEvent.NAME,
        event.getPayload());
  }
}
