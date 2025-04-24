package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.events.AccountUpdatedEvent;
import br.com.chronos.core.auth.domain.exceptions.AccountNotFoundException;
import br.com.chronos.core.auth.domain.exceptions.NotUpdateAccountExeception;
import br.com.chronos.core.auth.interfaces.AuthBroker;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.collaboration.domain.exceptions.ExistingEmailException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Email;

public class UpdateAccountUseCase {
  private final AccountsRepository accountsRepository;
  private final AuthBroker authBroker;

  public UpdateAccountUseCase(AccountsRepository accountsRepository, AuthBroker authBroker) {
    this.accountsRepository = accountsRepository;
    this.authBroker = authBroker;
  }

  public void execute(
      AccountDto updaterAccountDto,
      AccountDto collaboratorAccountDto,
      String collaboratorName,
      String collaboratorCpf,
      byte collaboratorWorkload) {
    var updaterAccount = new Account(updaterAccountDto);
    var collaboratorAccount = findAccountById(Id.create(collaboratorAccountDto.collaboratorId));
    var isSectorNotNull = Logical.create(collaboratorAccountDto.collaborationSector != null);

    if (updaterAccount.canUpdateOtherAccount(collaboratorAccount).isFalse()
        || updaterAccount.getRole().isAdmin().and(isSectorNotNull).isTrue()) {
      throw new NotUpdateAccountExeception();
    }
    if (collaboratorAccountDto.email != null) {
      var email = Email.create(collaboratorAccountDto.email);
      findAccountByExistingEmail(email);
    }

    accountsRepository.replace(collaboratorAccount);

    var event = new AccountUpdatedEvent(
        collaboratorAccountDto.collaboratorId,
        collaboratorName,
        collaboratorCpf,
        collaboratorWorkload);
    authBroker.publish(event);
  }

  public Account findAccountById(Id collaboratorId) {
    var account = accountsRepository.findByCollaborator(collaboratorId);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return account.get();
  }

  public Account findAccountByExistingEmail(Email email) {
    var account = accountsRepository.findByEmail(email);
    if (account.isPresent()) {
      throw new ExistingEmailException();
    }
    return account.get();
  }
}
