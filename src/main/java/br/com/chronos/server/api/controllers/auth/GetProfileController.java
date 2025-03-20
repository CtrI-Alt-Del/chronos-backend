package br.com.chronos.server.api.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.auth.use_cases.GetProfileUseCase;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;

@AuthController
public class GetProfileController{

  @Autowired
  private AccountsRepository repository;
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @GetMapping("/profile")
  public ResponseEntity<CollaboratorDto> handle(){
    var useCase = new GetProfileUseCase(repository);
    var responsible = authenticationProvider.getAuthenticatedUser();
    var response = useCase.execute(responsible);
    return ResponseEntity.ok(response);

  }

}
