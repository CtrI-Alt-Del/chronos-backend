package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.PageNumber;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.domain.records.Role.RoleName;
import br.com.chronos.core.modules.global.responses.PaginationResponse;

public class ListCollaboratorsUseCase {
  private final CollaboratorsRepository repository;

  public ListCollaboratorsUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<CollaboratorDto> execute(int page, RoleName requesterRole, Sector requesterSector,Logical isActive) {
    var collaboratorsCollection = repository.findMany(PageNumber.create(page), requesterRole, requesterSector,isActive);
    var dtos = collaboratorsCollection.getFirst().map(collaborator -> collaborator.getDto()).list();
    var itemsCount = collaboratorsCollection.getSecond();
    return new PaginationResponse<CollaboratorDto>(dtos, itemsCount.value());

  }

}
