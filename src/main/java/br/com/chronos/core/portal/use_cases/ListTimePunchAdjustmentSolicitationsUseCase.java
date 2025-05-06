package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.portal.domain.dtos.TimePunchAdjustmentSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ListTimePunchAdjustmentSolicitationsUseCase {
  private final SolicitationsRepository repository;

  public ListTimePunchAdjustmentSolicitationsUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<TimePunchAdjustmentSolicitationDto> execute(String collaborationSector, int page) {
    var response = repository.findManyTimePunchAdjustmentSolicitationsByCollaborationSector(
        CollaborationSector.create(collaborationSector),
        PageNumber.create(page));

    var dtos = response.getFirst().map((transaction) -> transaction.getDto()).list();
    var itemsCount = response.getSecond();
    return new PaginationResponse<TimePunchAdjustmentSolicitationDto>(dtos, itemsCount.value());
  }
}
