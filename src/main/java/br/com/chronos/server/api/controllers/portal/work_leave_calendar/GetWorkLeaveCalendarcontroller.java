package br.com.chronos.server.api.controllers.portal.work_leave_calendar;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.server.api.controllers.portal.solicitations.GetWorkLeaveCalendarUseCase;

@WorkLeaveCalendarController
public class GetWorkLeaveCalendarcontroller {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @GetMapping
  public ResponseEntity<PaginationResponse<WorkLeaveSolicitationDto>> handle(
      @RequestParam int year,
      @RequestParam int month,
      @RequestParam(defaultValue = "1") int page) {
    var account = authenticationProvider.getAccount();
    var sector = account.getCollaborationSector().toString();
    var useCase = new GetWorkLeaveCalendarUseCase(solicitationsRepository);
    var response = useCase.execute(sector, year, month, page);
    return ResponseEntity.ok(response);
  }
}
