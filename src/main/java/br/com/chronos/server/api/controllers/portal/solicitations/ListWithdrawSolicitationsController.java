
package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.ListWithdrawSolicitationUseCase;

@SolicitationsController
public class ListWithdrawSolicitationsController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @GetMapping("work-leave/withdraw")
  public ResponseEntity<PaginationResponse<WorkLeaveSolicitationDto>> handle(
      @RequestParam(defaultValue = "1") int page) {
    var account = authenticationProvider.getAccount();
    var sector = account.getCollaborationSector().toString();

    var useCase = new ListWithdrawSolicitationUseCase(solicitationsRepository);
    var response = useCase.execute(sector, page);
    return ResponseEntity.ok(response);
  }
}
