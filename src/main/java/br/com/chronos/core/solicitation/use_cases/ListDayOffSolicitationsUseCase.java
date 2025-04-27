package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.solicitation.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class ListDayOffSolicitationsUseCase {
  private final SolicitationsRepository repository;

  public ListDayOffSolicitationsUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<DayOffSolicitationDto> execute(String collaborationSector, int page) {
    var response = repository.findManyDayOffSolicitationsByCollaborationSector(
        CollaborationSector.create(collaborationSector),
        PageNumber.create(page));

    var dtos = response.getFirst().map((solicitation) -> solicitation.getDto()).list();
    var itemsCount = response.getSecond();
    return new PaginationResponse<DayOffSolicitationDto>(dtos, itemsCount.value());
  }
}
