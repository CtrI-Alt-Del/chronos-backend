
package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.CancelSolicitationUseCase;

@SolicitationsController
public class CancelSolicitationController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @PatchMapping("/{solicitationId}/cancel")
  public ResponseEntity<Void> handle(
      @PathVariable String solicitationId) {
    var useCase = new CancelSolicitationUseCase(solicitationsRepository);
    var account = authenticationProvider.getAccount();
    var responsible = new ResponsibleAggregateDto(
        new ResponsibleDto()
            .setId(account.getCollaboratorId().toString())
            .setRole(account.getRole().toString())
            .setSector(account.getCollaborationSector().toString()));

    useCase.execute(solicitationId, responsible);
    return ResponseEntity.noContent().build();
  }
}
