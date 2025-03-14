package br.com.chronos.server.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.server.database.jpa.collaborator.repositories.JpaCollaboratorRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaTimePunchesRepository;

@Configuration
public class DatabaseConfiguration {

  @Bean
  TimePunchesRepository timePunchesRepository() {
    return new JpaTimePunchesRepository();
  }

  @Bean
  CollaboratorsRepository collaboratorsRepository(){
    return new JpaCollaboratorRepository();
  }

}
