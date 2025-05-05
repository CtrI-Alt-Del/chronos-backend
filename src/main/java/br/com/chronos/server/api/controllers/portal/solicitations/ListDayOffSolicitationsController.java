package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.portal.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.ListDayOffSolicitationsUseCase;

@SolicitationsController
public class ListDayOffSolicitationsController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @GetMapping("/day-off")
  public ResponseEntity<PaginationResponse<DayOffSolicitationDto>> handle(
      @RequestParam(defaultValue = "1") int page) {
    var account = authenticationProvider.getAccount();
    var sector = account.getCollaborationSector().toString();

    var useCase = new ListDayOffSolicitationsUseCase(solicitationsRepository);
    var response = useCase.execute(sector, page);
    return ResponseEntity.ok(response);
  }
}
