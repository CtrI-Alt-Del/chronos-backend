package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.events.AuthenticationRequestedEvent;
import br.com.chronos.core.auth.domain.exceptions.AccountNotFoundException;
import br.com.chronos.core.auth.domain.exceptions.CredentialsNotValidException;
import br.com.chronos.core.auth.domain.records.Otp;
import br.com.chronos.core.auth.interfaces.AuthBroker;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.notification.interfaces.CacheProvider;

public class RequestAuthenticationUseCase {
  private final AuthenticationProvider authenticationProvider;
  private final CacheProvider cacheProvider;
  private final AccountsRepository repository;
  private final AuthBroker broker;

  public RequestAuthenticationUseCase(
      AuthenticationProvider authenticationProvider,
      CacheProvider cacheProvider,
      AccountsRepository repository,
      AuthBroker broker) {
    this.authenticationProvider = authenticationProvider;
    this.cacheProvider = cacheProvider;
    this.repository = repository;
    this.broker = broker;
  }

  public void execute(String email, String password) {
    var account = findAccount(Email.create(email));
    var isAuthenticated = authenticationProvider.validateCredentials(email, password);

    if (isAuthenticated.isFalse()) {
      throw new CredentialsNotValidException();
    }

    var otp = Otp.create();
    cacheProvider.setWithMinutesToExpire(
        otp.code().value(),
        account.getId().toString(),
        Otp.MINUTES_TO_EXPIRE);

    var event = new AuthenticationRequestedEvent(account, otp);
    broker.publish(event);
  }

  private Account findAccount(Email email) {
    var account = repository.findByEmail(email);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return account.get();
  }
}
