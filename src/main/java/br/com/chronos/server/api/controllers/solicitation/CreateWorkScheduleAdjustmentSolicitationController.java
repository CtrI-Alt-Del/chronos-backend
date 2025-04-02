package br.com.chronos.server.api.controllers.solicitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.solicitation.domain.dtos.WorkScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.use_cases.CreateWorkScheduleAdjustmentSolicitationUseCase;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import lombok.AllArgsConstructor;

@SolicitationsController
public class CreateWorkScheduleAdjustmentSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/work-schedule-adjustment")
  public ResponseEntity<WorkScheduleAdjustmentSolicitationDto> handle(
      @RequestBody WorkScheduleAdjustmentSolicitationDto body) {
    var useCase = new CreateWorkScheduleAdjustmentSolicitationUseCase(solicitationsRepository, workSchedulesRepository,
        collaboratorsRepository);
    var responsible = authenticationProvider.getAuthenticatedUser();
    var senderEmail = responsible.getEmail();
    var senderRole = responsible.getRole();
    var senderId = responsible.getCollaboratorId();
    var response = useCase.execute(body, senderId, senderEmail, senderRole);
    return ResponseEntity.ok(response);
  }
}
