package br.com.chronos.server.api.controllers.solicitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.solicitation.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.solicitation.use_cases.DeleteJustificationTypeUseCase;

@SolicitationsController
public class DeleteJustificationTypeController {

  @Autowired
  private JustificationTypeRepository justificationTypeRepository;

  @DeleteMapping("/justification-type/{justificationTypeId}")
  public ResponseEntity<Void> handle(@PathVariable("justificationTypeId") String justificationTypeId) {
    System.out.println(justificationTypeId);
    var useCase = new DeleteJustificationTypeUseCase(justificationTypeRepository);
    useCase.execute(justificationTypeId);
    return ResponseEntity.noContent().build();
  }
}
