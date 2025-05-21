package br.com.chronos.server.queue.rabbitmq.exchanges;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.collaboration.domain.events.CollaboratorsPreparedForWorkEvent;
import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.portal.domain.events.DayOffScheduleSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.TimePunchAdjusmentSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.WorkLeaveSolicitationApprovedEvent;
import br.com.chronos.server.queue.jobs.work_schedule.AdjustTimePunchJob;
import br.com.chronos.server.queue.jobs.work_schedule.CreateWorkLeaveJob;
import br.com.chronos.server.queue.jobs.work_schedule.CreateWorkdayLogsJob;
import br.com.chronos.server.queue.jobs.work_schedule.ExcuseWorkdayAbsenceJob;
import br.com.chronos.server.queue.jobs.work_schedule.ScheduleDayOffJob;
import br.com.chronos.server.queue.jobs.work_schedule.UpdateDayOffScheduleJob;
import br.com.chronos.server.queue.jobs.work_schedule.UpdateWorkdayHourBankJob;

@Configuration
public class WorkScheduleExchange {
  public static final String NAME = "work.schedule.exchange";

  @Bean
  DirectExchange workScheduleDirectExchange() {
    return new DirectExchange(NAME, true, false);
  }

  @Bean
  Queue createWorkdayLogsJobQueue() {
    return new Queue(CreateWorkdayLogsJob.KEY, true);
  }

  @Bean
  Queue createWorkLeaveJobQueue() {
    return new Queue(CreateWorkLeaveJob.KEY, true);
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
  Queue updateDayOffScheduleJobQueue() {
    return new Queue(UpdateDayOffScheduleJob.KEY, true);
  }

  Queue adjustTimePunchJob() {
    return new Queue(AdjustTimePunchJob.KEY, true);
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
        .with(WorkLeaveSolicitationApprovedEvent.NAME);
  }

  @Bean
  Binding excuseWorkdayAbsenceJobBinding() {
    return BindingBuilder
        .bind(excuseWorkdayAbsenceJobQueue())
        .to(workScheduleDirectExchange())
        .with(ExcusedAbsenceSolicitationApprovedEvent.NAME);
  }

  @Bean
  Binding adjustTimePunchJobBinding() {
    return BindingBuilder
        .bind(excuseWorkdayAbsenceJobQueue())
        .to(workScheduleDirectExchange())
        .with(TimePunchAdjusmentSolicitationApprovedEvent.NAME);
  }

  @Bean
  Binding scheduleDayOffJobBinding() {
    return BindingBuilder
        .bind(scheduleDayOffJobQueue())
        .to(workScheduleDirectExchange())
        .with(DayOffSolicitationApprovedEvent.NAME);
  }

  @Bean
  Binding updateDayOffScheduleJobBinding() {
    return BindingBuilder
        .bind(updateDayOffScheduleJobQueue())
        .to(workScheduleDirectExchange())
        .with(DayOffScheduleSolicitationApprovedEvent.NAME);
  }
}
