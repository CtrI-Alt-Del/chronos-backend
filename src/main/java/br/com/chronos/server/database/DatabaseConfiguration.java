package br.com.chronos.server.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffSolicitationRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.TimePunchLogAdjustmentRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.server.database.jpa.auth.repositories.JpaAccountsRepository;
import br.com.chronos.server.database.jpa.collaborator.repositories.JpaCollaboratorsRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaDayOffScheduleAdjustmentSolicitationsRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaDayOffSolicitationRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaJustificationRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaJustificationTypeRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaSolicitationsRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaTimePunchLogPunchAdjustmentSolicitationsRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaDayOffSchedulesRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaWorkdayLogsRepository;
import br.com.chronos.server.database.mongodb.hour_bank.repositories.MongoDbHourBankTransactionsRepository;

@Configuration
public class DatabaseConfiguration {
  @Bean
  WorkdayLogsRepository workdayLogsRepository() {
    return new JpaWorkdayLogsRepository();
  }

  @Bean
  DayOffSchedulesRepository DayOffSchedulesRepository() {
    return new JpaDayOffSchedulesRepository();
  }

  @Bean
  CollaboratorsRepository collaboratorsRepository() {
    return new JpaCollaboratorsRepository();
  }

  @Bean
  AccountsRepository accountsRepository() {
    return new JpaAccountsRepository();
  }

  @Bean
  SolicitationsRepository solicitationsRepository() {
    return new JpaSolicitationsRepository();
  }

  @Bean
  HourBankTransactionsRepository hourBankTransactionsRepository() {
    return new MongoDbHourBankTransactionsRepository();
  }

  @Bean
  TimePunchLogAdjustmentRepository timePunchAdjustmentRepository() {
    return new JpaTimePunchLogPunchAdjustmentSolicitationsRepository();
  }

  @Bean
  DayOffScheduleAdjustmentRepository dayOffSchedulesRepository() {
    return new JpaDayOffScheduleAdjustmentSolicitationsRepository();
  }

  @Bean
  JustificationTypeRepository justificationTypeRepository() {
    return new JpaJustificationTypeRepository();
  }
  @Bean
  JustificationRepository justificationRepository(){
    return new JpaJustificationRepository();
  }
  @Bean
  DayOffSolicitationRepository dayOffSolicitationRepository(){
    return new JpaDayOffSolicitationRepository();
  }
}
