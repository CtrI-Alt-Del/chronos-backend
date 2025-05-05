package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.portal.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ListDayOffScheduleAdjustmentSolicitationUseCase {
  private final SolicitationsRepository repository;

  public ListDayOffScheduleAdjustmentSolicitationUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<DayOffScheduleAdjustmentSolicitationDto> execute(String collaborationSector, int page) {
    var response = repository.findManyDayOffScheduleAdjustmentSolicitationsByCollaborationSector(
        CollaborationSector.create(collaborationSector),
        PageNumber.create(page));

    var dtos = response.getFirst().map((solicitation) ->
    solicitation.getDto()).list();
    var itemsCount = response.getSecond();
    return new PaginationResponse<DayOffScheduleAdjustmentSolicitationDto>(dtos,
    itemsCount.value());
  }
}
