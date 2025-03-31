package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.CreateCollaboratorUseCase;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.auth.domain.records.Password;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.modules.work_schedule.use_cases.CreateWorkdayLogUseCase;
import lombok.Data;

@CollaboratorsController
public class CreateCollaboratorController {

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private AccountsRepository accountsRepository;

  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Data
  private static class Request {
    CollaboratorDto collaboratorDto;
    String password;
  }

  @PostMapping
  public ResponseEntity<CollaboratorDto> handle(@RequestBody Request body) {
    var useCase = new CreateCollaboratorUseCase(accountsRepository, collaboratorsRepository, authenticationProvider);
    var password = Password.create(body.password);
    var responsible = authenticationProvider.getAuthenticatedUser();
    var responsibleSector = responsible.getSector().value();
    var responsibleRole = responsible.getRole();
    var collaboratorDto = useCase.execute(body.collaboratorDto, password, responsibleSector,
        responsibleRole);

    var workdayLogUseCase = new CreateWorkdayLogUseCase(
        workdayLogsRepository,
        workSchedulesRepository);
    workdayLogUseCase.execute(collaboratorDto.id, collaboratorDto.workScheduleId);
    return ResponseEntity.status(HttpStatus.OK).body(collaboratorDto);
  }
}
