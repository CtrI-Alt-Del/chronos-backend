package br.com.chronos.server.api.controllers.portal.justification_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.portal.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.portal.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.portal.use_cases.UpdateJustificationTypeUseCase;

@JustificationTypeController
public class UpdateJustificationTypeController {

  @Autowired
  private JustificationTypeRepository justificationTypeRepository;

  @PutMapping("/{justificationTypeId}")
  public ResponseEntity<Void> handle(@PathVariable String justificationTypeId,@RequestBody JustificationTypeDto body) {
    var useCase = new UpdateJustificationTypeUseCase(justificationTypeRepository);
    useCase.execute(justificationTypeId,body);
    return ResponseEntity.noContent().build();
  }
}
