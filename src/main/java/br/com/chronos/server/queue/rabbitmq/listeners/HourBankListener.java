package br.com.chronos.server.queue.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.chronos.core.solicitation.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.domain.events.PaidOvertimeSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceExcusedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayClosedEvent;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForDayOffJob;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForExcusedAbsenceJob;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForPaidOvertimeJob;
import br.com.chronos.server.queue.jobs.hour_bank.CreateHourBankTransactionForWorkdayJob;

@Component
public class HourBankListener {
  @Autowired
  private CreateHourBankTransactionForWorkdayJob createHourBankTransactionForWorkdayJob;

  @Autowired
  CreateHourBankTransactionForPaidOvertimeJob createHourBankTransactionForPaidOvertimeJob;

  @Autowired
  CreateHourBankTransactionForDayOffJob createHourBankTransactionForDayOffJob;

  @Autowired
  CreateHourBankTransactionForExcusedAbsenceJob createHourBankTransactionForExcusedAbsenceJob;

  @RabbitListener(queues = WorkdayClosedEvent.NAME, errorHandler = "rabbitMqErrorHandler")
  public void listen(@Payload WorkdayClosedEvent.Payload payload) {
    createHourBankTransactionForWorkdayJob.handle(payload);
  }

  @RabbitListener(queues = PaidOvertimeSolicitationApprovedEvent.NAME, errorHandler = "rabbitMqErrorHandler")
  public void listen(@Payload PaidOvertimeSolicitationApprovedEvent.Payload payload) {
    createHourBankTransactionForPaidOvertimeJob.handle(payload);
  }

  @RabbitListener(queues = DayOffSolicitationApprovedEvent.NAME, errorHandler = "rabbitMqErrorHandler")
  public void listen(@Payload DayOffSolicitationApprovedEvent.Payload payload) {
    createHourBankTransactionForDayOffJob.handle(payload);
  }

  @RabbitListener(queues = WorkdayAbsenceExcusedEvent.NAME, errorHandler = "rabbitMqErrorHandler")
  public void listen(@Payload WorkdayAbsenceExcusedEvent.Payload payload) {
    createHourBankTransactionForExcusedAbsenceJob.handle(payload);
  }
}
