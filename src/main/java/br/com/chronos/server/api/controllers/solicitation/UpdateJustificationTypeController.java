package br.com.chronos.server.api.controllers.solicitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.solicitation.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.solicitation.use_cases.UpdateJustificationTypeUseCase;

@SolicitationsController
public class UpdateJustificationTypeController {

  @Autowired
  private JustificationTypeRepository justificationTypeRepository;

  @PutMapping("/justification-type/{justificationTypeId}")
  public ResponseEntity<Void> handle(@PathVariable String justificationTypeId,@RequestBody JustificationTypeDto body) {
    var useCase = new UpdateJustificationTypeUseCase(justificationTypeRepository);
    useCase.execute(justificationTypeId,body);
    return ResponseEntity.noContent().build();
  }
}
