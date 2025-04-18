package br.com.chronos.server.api.controllers.solicitation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.chronos.core.solicitation.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.solicitation.use_cases.ListAllJustificationTypeUseCase;

@SolicitationsController
public class ListAllJustificationTypeController {
  @Autowired
  private JustificationTypeRepository justificationTypeRepository;

  @GetMapping("/justification-type")
  public ResponseEntity<List<JustificationTypeDto>> handle() {
    var useCase = new ListAllJustificationTypeUseCase(justificationTypeRepository);
    var response = useCase.execute();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
