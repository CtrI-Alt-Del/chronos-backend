package br.com.chronos.server.api.controllers.collaborators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.collaboration.use_cases.ListCollaboratorsUseCase;
import br.com.chronos.core.modules.global.responses.PaginationResponse;

@CollaboratorsController
public class ListCollaboratorsController {

  @Autowired
  private CollaboratorsRepository repository;

  @GetMapping
  public ResponseEntity<PaginationResponse<CollaboratorDto>> handle(@RequestParam(defaultValue = "1") int page) {
    var useCase = new ListCollaboratorsUseCase(repository);
    var response = useCase.execute(page);
    return ResponseEntity.ok(response);
  }

}
