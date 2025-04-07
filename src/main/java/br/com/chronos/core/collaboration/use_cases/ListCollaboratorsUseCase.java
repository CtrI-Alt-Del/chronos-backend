package br.com.chronos.core.collaboration.use_cases;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.global.domain.records.Role.RoleName;
import br.com.chronos.core.global.responses.PaginationResponse;

public class ListCollaboratorsUseCase {
  private final CollaboratorsRepository repository;

  public ListCollaboratorsUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<CollaboratorDto> execute(int page, RoleName requesterRole, Sector requesterSector,
      Logical isActive) {
    var collaboratorsCollection = repository.findMany(PageNumber.create(page), requesterRole, requesterSector,
        isActive);
    var dtos = collaboratorsCollection.getFirst().map(collaborator -> collaborator.getDto()).list();
    var itemsCount = collaboratorsCollection.getSecond();
    return new PaginationResponse<CollaboratorDto>(dtos, itemsCount.value());

  }

}
