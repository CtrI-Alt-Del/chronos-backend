package br.com.chronos.server.api.controllers.solicitation.solicitations;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.Data;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.solicitation.use_cases.DenySolicitationUseCase;

@SolicitationsController
public class DenySolicitationController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Data
  private static class Request {
    private String feedbackMessage;
  }

  @PatchMapping("/{solicitationId}/deny")
  public ResponseEntity<Void> handle(
      @PathVariable String solicitationId,
      @RequestBody Request body) {
    var useCase = new DenySolicitationUseCase(solicitationsRepository);
    var account = authenticationProvider.getAccount();
    var responsible = new ResponsibleAggregateDto(
        new ResponsibleDto()
            .setId(account.getCollaboratorId().toString())
            .setRole(account.getRole().toString())
            .setSector(account.getCollaborationSector().toString()));

    useCase.execute(solicitationId, responsible, body.getFeedbackMessage());
    return ResponseEntity.noContent().build();
  }
}
