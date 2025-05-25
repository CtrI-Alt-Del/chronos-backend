package br.com.chronos.server.api.controllers.portal.work_leave_calendar;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.portal.domain.dtos.CollaboratorWorkLeavesDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.server.api.controllers.portal.solicitations.GetWorkLeaveCalendarUseCase;
import br.com.chronos.server.api.services.CollaborationService;

@WorkLeaveCalendarController
public class GetWorkLeaveCalendarController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private CollaborationService collaborationService;

  @GetMapping
  public ResponseEntity<PaginationResponse<CollaboratorWorkLeavesDto>> handle(
      @RequestHeader("Authorization") String authorizationHeader,
      @RequestParam(defaultValue = "") String senderName,
      @RequestParam int year,
      @RequestParam int month,
      @RequestParam(defaultValue = "1") int page) {
    collaborationService.setJwt(authorizationHeader);
    var response = collaborationService.listCollaborators(senderName, page);
    List<ResponsibleDto> senders = new ArrayList<>();

    for (var collaborator : response.getItems()) {
      senders.add(new ResponsibleDto()
          .setId(collaborator.id)
          .setName(collaborator.name)
          .setEmail(collaborator.email)
          .setRole(collaborator.role)
          .setSector(collaborator.sector)
          .setCpf(collaborator.cpf));
    }

    var useCase = new GetWorkLeaveCalendarUseCase(solicitationsRepository);
    var collaboratorWorkLeaves = useCase.execute(senders, year, month);
    var paginationResponse = new PaginationResponse<CollaboratorWorkLeavesDto>(
        collaboratorWorkLeaves,
        response.getItemsCount());
    return ResponseEntity.ok(paginationResponse);
  }
}
