package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.events.AuthenticationRequestedEvent;
import br.com.chronos.core.auth.domain.exceptions.AccountNotFoundException;
import br.com.chronos.core.auth.domain.records.Otp;
import br.com.chronos.core.auth.interfaces.AuthBroker;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Password;
import br.com.chronos.core.notification.interfaces.CacheProvider;

public class RequestAuthenticationUseCase {
  private final AccountsRepository repository;
  private final CacheProvider cache;
  private final AuthBroker broker;

  public RequestAuthenticationUseCase(
      AccountsRepository repository,
      CacheProvider cache,
      AuthBroker broker) {
    this.repository = repository;
    this.cache = cache;
    this.broker = broker;
  }

  public void execute(String email, String password) {
    var account = findAccount(Email.create(email));
    account.validatePassword(Password.create(password));

    var otp = account.generateOtp();
    cache.setWithMinutesToExpire(
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
