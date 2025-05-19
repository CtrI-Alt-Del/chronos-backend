package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ListWithdrawSolicitationUseCase {
  private final SolicitationsRepository repository;

  public ListWithdrawSolicitationUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<WorkLeaveSolicitationDto> execute(String collaborationSector, int page) {
    var response = repository.findManyWorkLeaveSolicitationsByCollaborationSectorAndVacationStatus(
        CollaborationSector.create(collaborationSector),
        Logical.createAsFalse(),
        PageNumber.create(page));

    var dtos = response.getFirst().map((solicitation) -> solicitation.getDto()).list();
    var itemsCount = response.getSecond();
    return new PaginationResponse<WorkLeaveSolicitationDto>(dtos, itemsCount.value());
  }
}
