package br.com.chronos.server.api.controllers.collaboration.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.ListCollaboratorsUseCase;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.global.responses.PaginationResponse;

@CollaboratorsController
public class ListCollaboratorsController {

  @Autowired
  private CollaboratorsRepository repository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @GetMapping
  public ResponseEntity<PaginationResponse<CollaboratorDto>> handle(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "true", name = "active") boolean isActive) {
    var useCase = new ListCollaboratorsUseCase(repository);
    var account = authenticationProvider.getAccount();
    var response = useCase.execute(page, account.getRole().value(), account.getSector().value(), isActive);
    return ResponseEntity.ok(response);
  }

}
