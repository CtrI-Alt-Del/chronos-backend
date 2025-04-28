package br.com.chronos.server.api.controllers.solicitation.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.solicitation.domain.dtos.PaidOvertimeSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.solicitation.use_cases.ListPaidOvertimeSolicitationsUseCase;

@SolicitationsController
public class ListPaidOvertimeSolicitationsController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @GetMapping("/paid-overtime")
  public ResponseEntity<PaginationResponse<PaidOvertimeSolicitationDto>> handle(
      @RequestParam(defaultValue = "1") int page) {
    var account = authenticationProvider.getAccount();
    var sector = account.getCollaborationSector().toString();

    var useCase = new ListPaidOvertimeSolicitationsUseCase(solicitationsRepository);
    var response = useCase.execute(sector, page);
    return ResponseEntity.ok(response);
  }
}
