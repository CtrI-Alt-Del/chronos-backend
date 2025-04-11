package br.com.chronos.server.api.controllers.solicitation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.chronos.core.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.solicitation.use_cases.ListAllSolicitationsUseCase;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;

@SolicitationsController
public class ListAllSolicitationsController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @GetMapping("/solicitations")
  public ResponseEntity<List<SolicitationDto>> handle() {
    var useCase = new ListAllSolicitationsUseCase(solicitationsRepository);
    var sector = authenticationProvider.getAccount().getSector();
    var role = authenticationProvider.getAccount().getRole();
    var userId = authenticationProvider.getAccount().getCollaboratorId();
    var response = useCase.execute(sector, role, userId);
    return ResponseEntity.ok(response);
  }
}
