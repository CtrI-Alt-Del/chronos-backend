package br.com.chronos.server.api.controllers.solicitation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.chronos.core.modules.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.use_cases.ListAllSolicitationsUseCase;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;

@SolicitationsController
public class ListAllSolicitationsController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @GetMapping
  public ResponseEntity<List<SolicitationDto>> handle() {
    var useCase = new ListAllSolicitationsUseCase(solicitationsRepository);
    var sector = authenticationProvider.getAuthenticatedUser().getSector().value();
    var response = useCase.execute(sector);
    return ResponseEntity.ok(response);
  }
}
