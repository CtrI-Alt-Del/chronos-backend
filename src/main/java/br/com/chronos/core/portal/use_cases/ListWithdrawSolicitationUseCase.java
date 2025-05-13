package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.portal.domain.dtos.WithdrawSolicitationDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class ListWithdrawSolicitationUseCase {
  private final SolicitationsRepository repository;

  public ListWithdrawSolicitationUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<WithdrawSolicitationDto> execute(String collaborationSector, int page) {
    var response = repository.findManyWithdrawSolicitationsByCollaborationSector(
        CollaborationSector.create(collaborationSector),
        PageNumber.create(page));

    var dtos = response.getFirst().map((solicitation) -> solicitation.getDto()).list();
    var itemsCount = response.getSecond();
    return new PaginationResponse<WithdrawSolicitationDto>(dtos, itemsCount.value());
  }
}
