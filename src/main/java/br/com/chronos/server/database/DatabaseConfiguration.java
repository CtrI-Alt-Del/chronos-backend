package br.com.chronos.server.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.server.database.jpa.collaborator.repositories.JpaCollaboratorsRepository;
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

}
