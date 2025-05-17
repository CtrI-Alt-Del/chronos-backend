package br.com.chronos.server.api.controllers.collaboration.collaborators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.collaboration.interfaces.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.GetManagersEmailsUseCase;

@CollaboratorsController
public class GetAllManagersEmailsController {
  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @GetMapping("/managers/emails")
  public ResponseEntity<List<String>> handle(@RequestParam String sector) {
    var useCase = new GetManagersEmailsUseCase(collaboratorsRepository);
    var managersEmails = useCase.execute(sector);
    return ResponseEntity.ok(managersEmails);
  }
}
