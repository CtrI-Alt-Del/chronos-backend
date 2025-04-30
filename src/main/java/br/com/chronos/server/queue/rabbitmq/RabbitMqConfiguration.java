package br.com.chronos.server.queue.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;
import br.com.chronos.core.collaboration.domain.events.CollaboratorCreatedEvent;
import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.solicitation.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.domain.events.PaidOvertimeSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayClosedEvent;

@Configuration
public class RabbitMqConfiguration {
  @Bean
  Queue WorkdayClosedEventQueue() {
    return new Queue(WorkdayClosedEvent.NAME, true);
  }

  @Bean
  Queue CollaboratorCreatedEventQueue() {
    return new Queue(CollaboratorCreatedEvent.NAME, true);
  }

  @Bean
  Queue AccountUpdatedEventQueue() {
    return new Queue(AccountUpdatedEvent.NAME, true);
  }

  @Bean
  Queue CollaboratorsPreparedForWorkEventQueue() {
    return new Queue(CollaboratorsPreparedForWorkEvent.NAME, true);
  }

  @Bean
  Queue HourBankTransactionCreatedEventQueue() {
    return new Queue(HourBankTransactionCreatedEvent.NAME, true);
  }

  @Bean
  Queue PaidOvertimeSolicitationApprovedEventQueue() {
    return new Queue(PaidOvertimeSolicitationApprovedEvent.NAME, true);
  }

  @Bean
  Queue DayOffSolicitationApprovedEventQueue() {
    return new Queue(DayOffSolicitationApprovedEvent.NAME, true);
  }

  @Bean
  Queue ExcusedAbsenceSolicitationApprovedEventQueue() {
    return new Queue(ExcusedAbsenceSolicitationApprovedEvent.NAME, true);
  }

  @Bean
  Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
