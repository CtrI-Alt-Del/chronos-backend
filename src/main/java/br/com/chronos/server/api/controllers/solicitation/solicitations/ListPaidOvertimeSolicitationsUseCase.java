package br.com.chronos.server.api.controllers.solicitation.solicitations;

import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.solicitation.domain.dtos.PaidOvertimeSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class ListPaidOvertimeSolicitationsUseCase {
  private final SolicitationsRepository repository;

  public ListPaidOvertimeSolicitationsUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<PaidOvertimeSolicitationDto> execute(String collaborationSector, int page) {
    var response = repository.findManyPaidOvertimeSolicitationsByCollaborationSector(
        CollaborationSector.create(collaborationSector),
        PageNumber.create(page));

    var dtos = response.getFirst().map((transaction) -> transaction.getDto()).list();
    var itemsCount = response.getSecond();
    return new PaginationResponse<PaidOvertimeSolicitationDto>(dtos, itemsCount.value());
  }
}