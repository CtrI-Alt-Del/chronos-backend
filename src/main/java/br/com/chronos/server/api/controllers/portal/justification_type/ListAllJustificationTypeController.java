package br.com.chronos.server.api.controllers.portal.justification_type;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.chronos.core.portal.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.portal.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.portal.use_cases.ListAllJustificationTypesUseCase;

@JustificationTypeController
public class ListAllJustificationTypeController {
  @Autowired
  private JustificationTypeRepository justificationTypeRepository;

  @GetMapping
  public ResponseEntity<List<JustificationTypeDto>> handle() {
    var useCase = new ListAllJustificationTypesUseCase(justificationTypeRepository);
    var response = useCase.execute();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
