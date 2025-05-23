package br.com.chronos.server.api.controllers.portal.solicitations;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Month;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.work_schedule.domain.dtos.CollaboratorWorkLeaveDto;

public class GetWorkLeaveCalendarUseCase {
  private final SolicitationsRepository repository;

  public GetWorkLeaveCalendarUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<CollaboratorWorkLeaveDto> execute(
      String collaborationSector,
      int year,
      int month,
      int page) {
    var response = repository.findManyApprovedWorkLeaveSolicitationsByCollaborationSectorAndMonth(
        CollaborationSector.create(collaborationSector),
        Month.create(year, month),
        PageNumber.create(page));

    var workLeaves = response.getFirst().map(solicitation -> {
      var dto = new CollaboratorWorkLeaveDto()
          .setStartedAt(solicitation.getStartedAt().value())
          .setEndedAt(solicitation.getEndedAt().value())
          .setIsVacation(solicitation.getIsVacation().value())
          .setJustification(solicitation.getJustification().getDto())
          .setCollaborator(solicitation.getSenderResponsible().getDto().entity);
      return dto;
    });

    var itemsCount = response.getSecond();
    return new PaginationResponse<CollaboratorWorkLeaveDto>(workLeaves.list(), itemsCount.value());
  }
}
