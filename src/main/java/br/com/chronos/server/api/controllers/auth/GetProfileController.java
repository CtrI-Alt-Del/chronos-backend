package br.com.chronos.server.api.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.GetProfileUseCase;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;

@AuthController
public class GetProfileController {

  @Autowired
  private CollaboratorsRepository repository;
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @GetMapping("/profile")
  public ResponseEntity<CollaboratorDto> handle() {
    var useCase = new GetProfileUseCase(repository);
    var responsible = authenticationProvider.getAuthenticatedUser();
    var response = useCase.execute(responsible.getEmail());
    return ResponseEntity.ok(response);

  }

}
