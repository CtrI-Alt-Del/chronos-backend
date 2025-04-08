package br.com.chronos.server.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.solicitation.interfaces.repository.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.interfaces.repository.TimePunchLogAdjustmentRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.server.database.jpa.auth.repositories.JpaAccountsRepository;
import br.com.chronos.server.database.jpa.collaborator.repositories.JpaCollaboratorsRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaDayOffScheduleAdjustmentSolicitationsRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaSolicitationsRepository;
import br.com.chronos.server.database.jpa.solicitation.repositories.JpaTimePunchLogPunchAdjustmentSolicitationsRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaTimePunchesRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaWeekdaySchedulesRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaWorkSchedulesRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaWorkdayLogsRepository;
import br.com.chronos.server.database.jpa.work_schedule.repositories.JpaDayOffSchedulesRepository;

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
  WeekdaySchedulesRepository weekdaySchedulesRepository() {
    return new JpaWeekdaySchedulesRepository();
  }

  @Bean
  DayOffSchedulesRepository DayOffSchedulesRepository() {
    return new JpaDayOffSchedulesRepository();
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

  @Bean
  SolicitationsRepository solicitationsRepository() {
    return new JpaSolicitationsRepository();
  }

  @Bean
  TimePunchLogAdjustmentRepository timePunchAdjustmentRepository(){
    return new JpaTimePunchLogPunchAdjustmentSolicitationsRepository();
  }

  @Bean
  DayOffScheduleAdjustmentRepository dayOffSchedulesRepository() {
    return new JpaDayOffScheduleAdjustmentSolicitationsRepository();
  }

}
