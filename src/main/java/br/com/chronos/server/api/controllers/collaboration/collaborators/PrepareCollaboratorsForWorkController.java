package br.com.chronos.server.api.controllers.collaboration.collaborators;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import br.com.chronos.core.collaboration.interfaces.CollaborationBroker;
import br.com.chronos.core.collaboration.interfaces.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.PrepareCollaboratorsForWorkUseCase;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;

@CollaboratorsController
public class PrepareCollaboratorsForWorkController {
  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private CollaborationBroker collaborationBroker;

  @PostMapping("/work")
  public ResponseEntity<WorkdayLogDto> handle(@RequestParam LocalDate date) {
    var useCase = new PrepareCollaboratorsForWorkUseCase(collaboratorsRepository, collaborationBroker);
    useCase.execute(date);
    return ResponseEntity.noContent().build();
  }
}
