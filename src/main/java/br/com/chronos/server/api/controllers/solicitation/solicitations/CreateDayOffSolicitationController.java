package br.com.chronos.server.api.controllers.solicitation.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.solicitation.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.solicitation.use_cases.CreateDayOffSolicitationUseCase;

@SolicitationsController
public class CreateDayOffSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class Request extends DayOffSolicitationDto {
    private int workload;
  }

  @PostMapping("/day-off")
  public ResponseEntity<DayOffSolicitationDto> handle(@RequestBody DayOffSolicitationDto body) {
    var useCase = new CreateDayOffSolicitationUseCase(solicitationsRepository);
    var account = authenticationProvider.getAccount();
    var senderResponsibleId = account.getCollaboratorId().toString();
    var response = useCase.execute(body, senderResponsibleId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
