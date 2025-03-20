package br.com.chronos.core.modules.auth.use_cases;


import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;

public class GetProfileUseCase {

  private AccountsRepository repository;

  public GetProfileUseCase(AccountsRepository repository) {
    this.repository = repository;
  }

  public CollaboratorDto execute(Account account) {
    var email = account.getEmail();
    var profile = repository.findProfile(email);
    return profile.getDto();
  }

}
