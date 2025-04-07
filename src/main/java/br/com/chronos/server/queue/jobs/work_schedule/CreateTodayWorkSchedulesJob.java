package br.com.chronos.server.queue.jobs.work_schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.ListAllActiveCollaboratorsUseCase;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateTodayWorkdayLogsUseCase;

@Component
public class CreateTodayWorkSchedulesJob {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private WeekdaySchedulesRepository weekdaySchedulesRepository;

  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Scheduled(cron = "0 0 1 * * ?")
  public void handle() {
    var useCase = new CreateTodayWorkdayLogsUseCase(
        workdayLogsRepository,
        weekdaySchedulesRepository,
        dayOffSchedulesRepository);
    useCase.execute(listCollaboratorIds());
  }

  private List<String> listCollaboratorIds() {
    var useCase = new ListAllActiveCollaboratorsUseCase(collaboratorsRepository);
    var collaborators = useCase.execute();
    return collaborators.stream().map(collaborator -> collaborator.id).toList();
  }
}
