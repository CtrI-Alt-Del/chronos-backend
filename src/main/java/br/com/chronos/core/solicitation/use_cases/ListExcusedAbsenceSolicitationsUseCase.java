package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.solicitation.domain.dtos.ExcusedAbsenceSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class ListExcusedAbsenceSolicitationsUseCase {
  private final SolicitationsRepository repository;

  public ListExcusedAbsenceSolicitationsUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<ExcusedAbsenceSolicitationDto> execute(String collaborationSector, int page) {
    var response = repository.findManyExcusedAbsenceSolicitationsByCollaborationSector(
        CollaborationSector.create(collaborationSector),
        PageNumber.create(page));

    var dtos = response.getFirst().map((solicitation) -> solicitation.getDto()).list();
    var itemsCount = response.getSecond();
    return new PaginationResponse<ExcusedAbsenceSolicitationDto>(dtos, itemsCount.value());
  }
}
