package br.com.chronos.server.api.controllers.portal.solicitations;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Month;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class GetWorkLeaveCalendarUseCase {
  private final SolicitationsRepository repository;

  public GetWorkLeaveCalendarUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<WorkLeaveSolicitationDto> execute(
      String collaborationSector,
      int year,
      int month,
      int page) {
    var response = repository.findManyApprovedWorkLeaveSolicitationsByCollaborationSectorAndMonth(
        CollaborationSector.create(collaborationSector),
        Month.create(year, month),
        PageNumber.create(page));

    var workLeaves = response.getFirst().map(solicitation -> solicitation.getDto());
    var itemsCount = response.getSecond();
    return new PaginationResponse<WorkLeaveSolicitationDto>(workLeaves.list(), itemsCount.value());
  }
}
