package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.portal.domain.dtos.SolicitationDto;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.ApproveVacationSolicitationUseCase;
import lombok.Data;

@SolicitationsController
public class ApproveVacationSolicitationController {
    @Autowired
    private SolicitationsRepository solicitationsRepository;

    @Autowired
    private PortalBroker portalBroker;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Data
    public static class Request {
        private String feedbackMessage;
    }

    @PutMapping("/{solicitationId}/approve/vacation")
    public ResponseEntity<SolicitationDto> handle(
            @PathVariable String solicitationId,
            @RequestBody Request body) {
            var useCase = new ApproveVacationSolicitationUseCase(solicitationsRepository, portalBroker);
            var account = authenticationProvider.getAccount();
            var responsible = new ResponsibleAggregateDto(
                    new ResponsibleDto()
                            .setId(account.getCollaboratorId().toString())
                            .setRole(account.getRole().toString())
                            .setSector(account.getCollaborationSector().toString()));
            
            useCase.execute(
                solicitationId,
                responsible,
                body.getFeedbackMessage());

            return ResponseEntity.noContent().build();
    }

    
}
