package br.com.chronos.server.api.controllers.work_schedule.workday_logs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.ListAllActiveCollaboratorsUseCase;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateTodayWorkdayLogsUseCase;

@WorkdayLogsController
public class CreateTodayWorkdayLogsController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @PostMapping("/today")
  public ResponseEntity<WorkdayLogDto> handle() {
    var useCase = new CreateTodayWorkdayLogsUseCase(workdayLogsRepository, dayOffSchedulesRepository);

    useCase.execute(listCollaboratorIds());
    return ResponseEntity.noContent().build();
  }

  private List<String> listCollaboratorIds() {
    var useCase = new ListAllActiveCollaboratorsUseCase(collaboratorsRepository);
    var collaborators = useCase.execute();
    return collaborators.stream().map(collaborator -> collaborator.id).toList();
  }
}
