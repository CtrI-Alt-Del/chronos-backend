package br.com.chronos.server.queue.rabbitmq.brokers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.events.DayOffScheduleSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.PaidOvertimeSolicitationApprovedEvent;
import br.com.chronos.core.portal.interfaces.PortalBroker;

@Component
public class RabbitMqPortalBroker implements PortalBroker {
  private final RabbitTemplate rabbit;

  public RabbitMqPortalBroker(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Override
  public void publish(PaidOvertimeSolicitationApprovedEvent event) {
    rabbit.convertAndSend("", PaidOvertimeSolicitationApprovedEvent.NAME, event.getPayload());
  }

  @Override
  public void publish(ExcusedAbsenceSolicitationApprovedEvent event) {
    rabbit.convertAndSend("", ExcusedAbsenceSolicitationApprovedEvent.NAME, event.getPayload());
  }

  @Override
  public void publish(DayOffSolicitationApprovedEvent event) {
    rabbit.convertAndSend("", DayOffSolicitationApprovedEvent.NAME, event.getPayload());
  }

  @Override
  public void publish(DayOffScheduleSolicitationApprovedEvent event) {
    rabbit.convertAndSend("", DayOffScheduleSolicitationApprovedEvent.NAME, event.getPayload());
  }
}
