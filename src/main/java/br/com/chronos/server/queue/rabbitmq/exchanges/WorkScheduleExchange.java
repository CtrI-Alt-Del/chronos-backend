package br.com.chronos.server.queue.rabbitmq.exchanges;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.solicitation.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.server.queue.jobs.work_schedule.CreateWorkdayLogsJob;
import br.com.chronos.server.queue.jobs.work_schedule.ExcuseWorkdayAbsenceJob;
import br.com.chronos.server.queue.jobs.work_schedule.ScheduleDayOffJob;
import br.com.chronos.server.queue.jobs.work_schedule.UpdateWorkdayHourBankJob;

@Configuration
public class WorkScheduleExchange {
  @Bean
  DirectExchange workScheduleDirectExchange() {
    return new DirectExchange("work.schedule.exchange", true, false);
  }

  @Bean
  Queue createWorkdayLogsJobQueue() {
    return new Queue(CreateWorkdayLogsJob.KEY, true);
  }

  @Bean
  Queue updateWorkdayHourBankJobQueue() {
    return new Queue(UpdateWorkdayHourBankJob.KEY, true);
  }

  @Bean
  Queue excuseWorkdayAbsenceJobQueue() {
    return new Queue(ExcuseWorkdayAbsenceJob.KEY, true);
  }

  @Bean
  Queue scheduleDayOffJobQueue() {
    return new Queue(ScheduleDayOffJob.KEY, true);
  }

  @Bean
  Binding createWorkdayLogsJobBinding() {
    return BindingBuilder
        .bind(createWorkdayLogsJobQueue())
        .to(workScheduleDirectExchange())
        .with(CollaboratorsPreparedForWorkEvent.NAME);
  }

  @Bean
  Binding updateWorkdayHourBankJobBinding() {
    return BindingBuilder
        .bind(updateWorkdayHourBankJobQueue())
        .to(workScheduleDirectExchange())
        .with(HourBankTransactionCreatedEvent.NAME);
  }

  @Bean
  Binding excuseWorkdayAbsenceJobBinding() {
    return BindingBuilder
        .bind(excuseWorkdayAbsenceJobQueue())
        .to(workScheduleDirectExchange())
        .with(ExcusedAbsenceSolicitationApprovedEvent.NAME);
  }

  @Bean
  Binding scheduleDayOffJobBinding() {
    return BindingBuilder
        .bind(scheduleDayOffJobQueue())
        .to(workScheduleDirectExchange())
        .with(DayOffSolicitationApprovedEvent.NAME);
  }
}
