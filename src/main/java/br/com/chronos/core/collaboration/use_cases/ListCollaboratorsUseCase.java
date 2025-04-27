package br.com.chronos.core.collaboration.use_cases;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Role;
import br.com.chronos.core.global.responses.PaginationResponse;
import kotlin.Pair;

public class ListCollaboratorsUseCase {
  private final CollaboratorsRepository repository;

  public ListCollaboratorsUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<CollaboratorDto> execute(
      int page, String accountRole, String requesterSector, boolean isCollaboratorActive, String requesterId) {
    var role = Role.create(accountRole);
    Pair<Array<Collaborator>, PlusIntegerNumber> response;

    if (role.isAdmin().isTrue()) {
      response = repository.findMany(Logical.create(isCollaboratorActive), PageNumber.create(page));
    } else {
      response = repository.findManyByCollaborationSector(
          Id.create(requesterId),
          CollaborationSector.create(requesterSector),
          Logical.create(isCollaboratorActive),

          PageNumber.create(page));
    }

    var dtos = response.getFirst().map(collaborator -> collaborator.getDto()).list();
    var itemsCount = response.getSecond();
    return new PaginationResponse<CollaboratorDto>(dtos, itemsCount.value());
  }

}
