package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.responses.PaginationResponse;

public class ListCollaboratorsUseCase {
  private final CollaboratorsRepository repository;

  public ListCollaboratorsUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<CollaboratorDto> execute(int page, int itemsPerPage) {
    var collaboratorsCollection = repository.findMany(page, itemsPerPage);
    var dtos = collaboratorsCollection.getFirst().map(collaborator -> collaborator.getDto()).items();
    var itemsCount = collaboratorsCollection.getSecond();
    return new PaginationResponse<CollaboratorDto>(dtos, itemsCount.intValue());

  }

}
