package br.com.chronos.core.modules.auth.use_cases;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Email;

public class GetAccountUseCase {
  private CollaboratorsRepository repository;

  public GetAccountUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }
  public CollaboratorDto execute(String email){
    var collaborator = repository.findByEmail(Email.create(email,"collaborator email"));
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();
    }
    return collaborator.get().getDto();
  }

}
