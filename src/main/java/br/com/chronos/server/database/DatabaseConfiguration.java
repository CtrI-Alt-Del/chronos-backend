package br.com.chronos.server.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.server.database.jpa.auth.repositories.JpaAccountsRepository;
import br.com.chronos.server.database.jpa.collaborator.repositories.JpaCollaboratorsRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaSolicitationsRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaTimePunchesRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaWorkSchedulesRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaWorkdayLogsRepository;

@Configuration
public class DatabaseConfiguration {

  @Bean
  TimePunchesRepository timePunchesRepository() {
    return new JpaTimePunchesRepository();
  }

  @Bean
  SolicitationsRepository solicitationsRepository(){
    return new JpaSolicitationsRepository();
  }

  @Bean
  WorkdayLogsRepository workdayLogsRepository() {
    return new JpaWorkdayLogsRepository();
  }

  @Bean
  WorkSchedulesRepository workSchedulesRepository() {
    return new JpaWorkSchedulesRepository();
  }

  @Bean
  CollaboratorsRepository collaboratorsRepository() {
    return new JpaCollaboratorsRepository();
  }

  @Bean
  AccountsRepository accountsRepository() {
    return new JpaAccountsRepository();
  }

}
