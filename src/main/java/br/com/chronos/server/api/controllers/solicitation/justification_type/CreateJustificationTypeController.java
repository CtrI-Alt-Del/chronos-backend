package br.com.chronos.server.api.controllers.solicitation.justification_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.solicitation.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.solicitation.use_cases.CreateJustificationTypeUseCase;

@JustificationTypeController
public class CreateJustificationTypeController{
  @Autowired
  private JustificationTypeRepository justificationTypeRepository;

  @PostMapping
  public ResponseEntity<JustificationTypeDto> handle(@RequestBody JustificationTypeDto body) {
    var useCase = new CreateJustificationTypeUseCase(justificationTypeRepository);
    var response = useCase.execute(body);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
