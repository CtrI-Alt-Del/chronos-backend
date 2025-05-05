package br.com.chronos.server.api.controllers.portal.justification_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.portal.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.portal.use_cases.DeleteJustificationTypeUseCase;

@JustificationTypeController
public class DeleteJustificationTypeController {

  @Autowired
  private JustificationTypeRepository justificationTypeRepository;

  @DeleteMapping("/{justificationTypeId}")
  public ResponseEntity<Void> handle(@PathVariable("justificationTypeId") String justificationTypeId) {
    System.out.println(justificationTypeId);
    var useCase = new DeleteJustificationTypeUseCase(justificationTypeRepository);
    useCase.execute(justificationTypeId);
    return ResponseEntity.noContent().build();
  }
}
