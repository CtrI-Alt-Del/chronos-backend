package br.com.chronos.server.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.collaboration.interfaces.CollaboratorsRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkLeavesRepository;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.portal.interfaces.repositories.AttachmentRepository;
import br.com.chronos.core.portal.interfaces.repositories.JustificationRepository;
import br.com.chronos.core.portal.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.server.database.jpa.auth.repositories.JpaAccountsRepository;
import br.com.chronos.server.database.jpa.collaborator.repositories.JpaCollaboratorsRepository;
import br.com.chronos.server.database.jpa.portal.repositories.JpaAttachmentRepository;
import br.com.chronos.server.database.jpa.portal.repositories.JpaJustificationRepository;
import br.com.chronos.server.database.jpa.portal.repositories.JpaJustificationTypeRepository;
import br.com.chronos.server.database.jpa.portal.repositories.JpaSolicitationsRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaDayOffSchedulesRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaWorkLeavesRepository;
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
  JustificationTypeRepository justificationTypeRepository() {
    return new JpaJustificationTypeRepository();
  }

  @Bean
  JustificationRepository justificationRepository() {
    return new JpaJustificationRepository();
  }

  @Bean
  AttachmentRepository attachmentRepository() {
    return new JpaAttachmentRepository();
  }

  @Bean
  WorkLeavesRepository workLeavesRepository() {
    return new JpaWorkLeavesRepository();
  }
}
