package br.com.chronos.server.queue.rabbitmq.exchanges;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceExcusedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayClosedEvent;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForDayOffJob;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForExcusedAbsenceJob;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForWorkdayJob;

@Configuration
public class HourBankExchange {
  @Bean
  DirectExchange hourBankDirectExchange() {
    return new DirectExchange("hour.bank.direct.exchange", true, false);
  }

  @Bean
  Queue createHourBankTransactionForDayOffJobQueue() {
    return new Queue(CreateHourBankTransactionForDayOffJob.KEY, true);
  }

  @Bean
  Queue createHourBankTransactionForExcusedAbsenceJobQueue() {
    return new Queue(CreateHourBankTransactionForExcusedAbsenceJob.KEY, true);
  }

  @Bean
  Queue createHourBankTransactionForWorkdayJobQueue() {
    return new Queue(CreateHourBankTransactionForWorkdayJob.KEY, true);
  }

  @Bean
  Binding createHourBankTransactionForDayOffJobBinding() {
    return BindingBuilder
        .bind(createHourBankTransactionForDayOffJobQueue())
        .to(hourBankDirectExchange())
        .with(DayOffSolicitationApprovedEvent.NAME);
  }

  @Bean
  Binding createHourBankTransactionForExcusedAbsenceJobBinding() {
    return BindingBuilder
        .bind(createHourBankTransactionForExcusedAbsenceJobQueue())
        .to(hourBankDirectExchange())
        .with(WorkdayAbsenceExcusedEvent.NAME);
  }

  @Bean
  Binding CreateHourBankTransactionForWorkdayJobBinding() {
    return BindingBuilder
        .bind(createHourBankTransactionForWorkdayJobQueue())
        .to(hourBankDirectExchange())
        .with(WorkdayClosedEvent.NAME);
  }
}
