package br.com.chronos.server.api.controllers.solicitation.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.solicitation.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffSolicitationRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationRepository;
import br.com.chronos.core.solicitation.use_cases.CreateDayOffSolicitationUseCase;

@SolicitationsController
public class CreateDayOffSolicitationController {

  @Autowired
  private DayOffSolicitationRepository solicitationsRepository;

  @Autowired
  private JustificationRepository justificationRepository;


  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PostMapping("/day-off")
  public ResponseEntity<DayOffSolicitationDto> handle(
      @RequestBody DayOffSolicitationDto body) {
    var useCase = new CreateDayOffSolicitationUseCase(solicitationsRepository,justificationRepository);
    var responsible = authenticationProvider.getAccount();
    var senderId = responsible.getCollaboratorId();
    var response = useCase.execute(body, senderId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
